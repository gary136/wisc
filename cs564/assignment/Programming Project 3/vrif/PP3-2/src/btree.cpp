/**
 * @author See Contributors.txt for code contributors and overview of BadgerDB.
 *
 * @section LICENSE
 * Copyright (c) 2012 Database Group, Computer Sciences Department, University of Wisconsin-Madison.
 */

#include "btree.h"
#include "filescan.h"
#include "exceptions/bad_index_info_exception.h"
#include "exceptions/bad_opcodes_exception.h"
#include "exceptions/bad_scanrange_exception.h"
#include "exceptions/no_such_key_found_exception.h"
#include "exceptions/scan_not_initialized_exception.h"
#include "exceptions/index_scan_completed_exception.h"
#include "exceptions/file_not_found_exception.h"
#include "exceptions/end_of_file_exception.h"


//#define DEBUG

namespace badgerdb
{

// -----------------------------------------------------------------------------
// BTreeIndex::BTreeIndex -- Constructor
// -----------------------------------------------------------------------------

BTreeIndex::BTreeIndex(const std::string & relationName,
		std::string & outIndexName,
		BufMgr *bufMgrIn,
		const int attrByteOffset,
		const Datatype attrType)
{
	std::ostringstream idxStr;
	idxStr << relationName << ',' << attrByteOffset;
	outIndexName = idxStr.str();

	this->relationName = relationName;
	this->attrByteOffset = attrByteOffset;

	this->bufMgr = bufMgrIn;
	this->attributeType = attrType;

	if (File::exists(outIndexName)) {
		// Read meta info
		file = new BlobFile(BlobFile::open(outIndexName));
		Page metaInfoPage = file->readPage(indexMetaInfoPage);
		IndexMetaInfo metaInfo = reinterpret_cast<IndexMetaInfo&>(metaInfoPage);
		this->rootPageNum = metaInfo.rootPageNo;
		this->numLevels = metaInfo.numLevels;
		this->order = metaInfo.order;

		if (metaInfo.attrByteOffset != attrByteOffset) {
			throw BadIndexInfoException("AttrByteOffset does not match.");
		} else if (metaInfo.attrType != attrType) {
			throw BadIndexInfoException("AttrType does not match.");
		} else if (metaInfo.relationName != relationName) {
			throw BadIndexInfoException("RelationName does not match.");
		}
	} else {
		this->rootPageNum = 2; // default root page number is 2
		this->numLevels = 1;
		this->order = 64;

		// Create & write meta info
		file = new BlobFile(BlobFile::create(outIndexName));
		writeMetaInfo(true);

		// create root page
		PageId pageNo;
		LeafNodeInt *rootNode;
		bufMgr->allocPage(file, pageNo, reinterpret_cast<Page*&>(rootNode));
		assert(pageNo == this->rootPageNum);
		rootNode->numValidKeys = 0;
		rootNode->rightSibPageNo = 0;
		bufMgr->unPinPage(file, pageNo, true);

		// insert data
		FileScan scanner(relationName, bufMgr);
		while (true) {
			RecordId rid;
			try {
				scanner.scanNext(rid);
			} catch(EndOfFileException &e) {
				break;
			}

			std::string record = scanner.getRecord();
			insertEntry(record.data() + attrByteOffset, rid);
		}
	}
}


// -----------------------------------------------------------------------------
// BTreeIndex::~BTreeIndex -- destructor
// -----------------------------------------------------------------------------

BTreeIndex::~BTreeIndex()
{
	if (this->scanExecuting) endScan();
	bufMgr->flushFile(file);
	delete file;
}

// -----------------------------------------------------------------------------
// BTreeIndex::insertEntry
// -----------------------------------------------------------------------------

void BTreeIndex::insertEntry(const void *key, const RecordId rid) 
{
	int entry = *reinterpret_cast<const int*>(key);

	std::vector<PageId> parents;
	PageId currPageNum = searchLeaf(entry, &parents);

	LeafNodeInt *leafNode;
	bufMgr->readPage(file, currPageNum, reinterpret_cast<Page*&>(leafNode));
	int idx = 0;
	while (idx < leafNode->numValidKeys && leafNode->keyArray[idx] <= entry) {
		++idx;
	}
	for (int i = leafNode->numValidKeys - 1; i >= idx; --i) {
		leafNode->keyArray[i + 1] = leafNode->keyArray[i];
		leafNode->ridArray[i + 1] = leafNode->ridArray[i];
	}
	leafNode->keyArray[idx] = entry;
	leafNode->ridArray[idx] = rid;
	++leafNode->numValidKeys;

	if (leafNode->numValidKeys == (this->order * 2 + 1)) {
		PageId newPageNum;
		LeafNodeInt *newLeafNode;
		bufMgr->allocPage(file, newPageNum, reinterpret_cast<Page*&>(newLeafNode));
		for (int i = this->order; i < leafNode->numValidKeys; ++i) {
			newLeafNode->keyArray[i - this->order] = leafNode->keyArray[i];
			newLeafNode->ridArray[i - this->order] = leafNode->ridArray[i];
		}
		leafNode->numValidKeys = this->order;
		newLeafNode->numValidKeys = this->order + 1;
		newLeafNode->rightSibPageNo = leafNode->rightSibPageNo;
		leafNode->rightSibPageNo = newPageNum;

		int splitKey = newLeafNode->keyArray[0];

		bufMgr->unPinPage(file, currPageNum, true);
		bufMgr->unPinPage(file, newPageNum, true);

		bool needNewRoot = true;
		while (!parents.empty()) {
			currPageNum = parents.back();
			parents.pop_back();

			NonLeafNodeInt *currNode;
			bufMgr->readPage(file, currPageNum, reinterpret_cast<Page*&>(currNode));

			int idx = 0;
			while (idx < currNode->numValidKeys && splitKey >= currNode->keyArray[idx]) {
				++idx;
			}
			for (int i = currNode->numValidKeys - 1; i >= idx; --i) {
				currNode->keyArray[i + 1] = currNode->keyArray[i];
				currNode->pageNoArray[i + 2] = currNode->pageNoArray[i + 1];
			}
			currNode->keyArray[idx] = splitKey;
			currNode->pageNoArray[idx + 1] = newPageNum;
			++currNode->numValidKeys;

			if (currNode->numValidKeys <= this->order * 2) {
				needNewRoot = false;
				bufMgr->unPinPage(file, currPageNum, true);
				break;
			} else {
				NonLeafNodeInt *newNonLeafNode;
				bufMgr->allocPage(file, newPageNum, reinterpret_cast<Page*&>(newNonLeafNode));
				for (int i = this->order + 1; i < currNode->numValidKeys; ++i) {
					newNonLeafNode->keyArray[i - (this->order + 1)] = currNode->keyArray[i];
					newNonLeafNode->pageNoArray[i - (this->order + 1)] = currNode->pageNoArray[i];
				}
				newNonLeafNode->pageNoArray[this->order] =
								currNode->pageNoArray[currNode->numValidKeys];
				currNode->numValidKeys = this->order;
				newNonLeafNode->numValidKeys = this->order;
				splitKey = currNode->keyArray[this->order];
				bufMgr->unPinPage(file, currPageNum, true);
				bufMgr->unPinPage(file, newPageNum, true);
			}
		}
		// allocate new root node if needed
		if (needNewRoot) {
			NonLeafNodeInt *newRootNode;
			PageId newRootPageNum;
			bufMgr->allocPage(file, newRootPageNum, reinterpret_cast<Page*&>(newRootNode));
			newRootNode->keyArray[0] = splitKey;
			newRootNode->pageNoArray[0] = currPageNum;
			newRootNode->pageNoArray[1] = newPageNum;
			newRootNode->numValidKeys = 1;
			this->rootPageNum = newRootPageNum;
			++this->numLevels;
			writeMetaInfo();
			bufMgr->unPinPage(file, newRootPageNum, true);
		}
	} else {
		bufMgr->unPinPage(file, currPageNum, true);
	}
}

// -----------------------------------------------------------------------------
// BTreeIndex::startScan
// -----------------------------------------------------------------------------

void BTreeIndex::startScan(const void* lowValParm,
				   const Operator lowOpParm,
				   const void* highValParm,
				   const Operator highOpParm)
{
	if (this->scanExecuting) endScan();

	int lowSearchVal = *reinterpret_cast<const int*>(lowValParm);
	int highSearchVal = *reinterpret_cast<const int*>(highValParm);
	if (lowOpParm != GT && lowOpParm != GTE) {
		throw BadOpcodesException();
	}
	if (highOpParm != LT && highOpParm != LTE) {
		throw BadOpcodesException();
	}
	if (lowSearchVal > highSearchVal) {
		throw BadScanrangeException();
	}

	this->lowValInt = lowSearchVal;
	this->lowOp = lowOpParm;
	this->highValInt = highSearchVal;
	this->highOp = highOpParm;
	this->currentPageNum = searchLeaf(lowSearchVal, nullptr);
	bufMgr->readPage(this->file, this->currentPageNum, reinterpret_cast<Page*&>(this->currentPageData));
	this->scanExecuting = true;

	this->nextEntry = 0;
	auto lowOpFunc = lowOpParm == GT ?
			[](int searchVal, int key) { return key > searchVal; } :
			[](int searchVal, int key) { return key >= searchVal; };
	while (this->nextEntry < this->currentPageData->numValidKeys &&
		!lowOpFunc(lowSearchVal, this->currentPageData->keyArray[this->nextEntry])) {
		++this->nextEntry;
	}
}

// -----------------------------------------------------------------------------
// BTreeIndex::scanNext
// -----------------------------------------------------------------------------

void BTreeIndex::scanNext(RecordId& outRid) 
{
	if (!this->scanExecuting) throw ScanNotInitializedException();

	if (this->nextEntry >= this->currentPageData->numValidKeys) {
		PageId nextPage = this->currentPageData->rightSibPageNo;
		bufMgr->unPinPage(file, this->currentPageNum, false);
		this->currentPageNum = nextPage;
		if (nextPage) {
			bufMgr->readPage(file, nextPage, reinterpret_cast<Page*&>(this->currentPageData));
			this->nextEntry = 0;
		} else {
			throw IndexScanCompletedException();
		}
	}

	auto highOpFunc = this->highOp == LT ?
			[](int searchVal, int key) { return key < searchVal; } :
			[](int searchVal, int key) { return key <= searchVal; };
	if (!highOpFunc(highValInt, this->currentPageData->keyArray[this->nextEntry])) {
		throw IndexScanCompletedException();
	}

	outRid = this->currentPageData->ridArray[this->nextEntry];
	++this->nextEntry;
}

// -----------------------------------------------------------------------------
// BTreeIndex::endScan
// -----------------------------------------------------------------------------
//
void BTreeIndex::endScan() 
{
	if (!this->scanExecuting) throw ScanNotInitializedException();

	this->scanExecuting = false;
	if (this->currentPageNum != 0) {
		bufMgr->unPinPage(file, this->currentPageNum, false);
	}
}

void BTreeIndex::writeMetaInfo(bool alloc)
{
	IndexMetaInfo *metaInfo;
	if (alloc) {
		PageId pageNo;
		bufMgr->allocPage(
			file, pageNo, reinterpret_cast<Page*&>(metaInfo));
		assert(pageNo == indexMetaInfoPage);
	} else {
		bufMgr->readPage(
			file, indexMetaInfoPage, reinterpret_cast<Page*&>(metaInfo));
	}

	metaInfo->attrByteOffset = attrByteOffset;
	metaInfo->attrType = this->attributeType;
	strncpy(metaInfo->relationName, this->relationName.c_str(),
					sizeof(metaInfo->relationName));
	metaInfo->rootPageNo = this->rootPageNum;
	metaInfo->numLevels = this->numLevels;
	metaInfo->order = this->order;

	bufMgr->unPinPage(file, indexMetaInfoPage, true);
}

PageId BTreeIndex::searchLeaf(int entry, std::vector<PageId> *parents)
{
	PageId currPageNum = this->rootPageNum;
	int currLevel = 0;
	while (currLevel < this->numLevels - 1) {
		NonLeafNodeInt *currNode;
		bufMgr->readPage(file, currPageNum, reinterpret_cast<Page*&>(currNode));

		PageId nextPageNum = currNode->pageNoArray[currNode->numValidKeys];
		for (int i = 0; i < currNode->numValidKeys; ++i) {
			if (entry < currNode->keyArray[i]) {
				nextPageNum = currNode->pageNoArray[i];
				break;
			}
		}

		if (parents) parents->push_back(currPageNum);
		bufMgr->unPinPage(file, currPageNum, false);
		currPageNum = nextPageNum;
		++currLevel;
	}
	return currPageNum;
}

}
