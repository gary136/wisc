/**
 * @author See Contributors.txt for code contributors and overview of BadgerDB.
 *
 * @section LICENSE
 * Copyright (c) 2012 Database Group, Computer Sciences Department, University
 * of Wisconsin-Madison.
 */

#include "buffer.h"

#include <iostream>
#include <memory>
// #include <algorithm>

#include "exceptions/bad_buffer_exception.h"
#include "exceptions/buffer_exceeded_exception.h"
#include "exceptions/hash_not_found_exception.h"
#include "exceptions/page_not_pinned_exception.h"
#include "exceptions/page_pinned_exception.h"

#include "bufHashTbl.h"

namespace badgerdb {

constexpr int HASHTABLE_SZ(int bufs) { return ((int)(bufs * 1.2) & -2) + 1; }

//----------------------------------------
// Constructor of the class BufMgr
//----------------------------------------

BufMgr::BufMgr(std::uint32_t bufs)
    // initialization list
    : numBufs(bufs), // numBufs=bufs
      hashTable(HASHTABLE_SZ(bufs)),
      bufDescTable(bufs),
      bufPool(bufs) {
  for (FrameId i = 0; i < bufs; i++) {
    bufDescTable[i].frameNo = i;
    bufDescTable[i].valid = false;
  }

  clockHand = bufs - 1; // clockHand = 0 ~ bufs-1 
  // std::cout << "numBufs=" << numBufs << ", clockHand=" << clockHand << "\n";
}

void BufMgr::advanceClock() {
  // Advance clock to next frame in the buffer pool.
  // std::cout << "clockHand " << clockHand;
  clockHand=(clockHand+1)%numBufs; 
  // std::cout << " -> " << clockHand << "\n";
}

void BufMgr::allocBuf(FrameId& frame) { // pass by reference
  // Allocates a free frame using the clock algorithm; 
  frame = clockHand; // init  
  // The frame variable is a reference, so the function works by 
  // assigning the number to this variable.
  bool ready = false;
  int cnt = 0;

  while (!ready) {
    cnt++;
    if (cnt > 2 * numBufs) { // clock has circled around
      // Throws BufferExceededException if all buffer frames are pinned. 
      throw BufferExceededException();
    }
    // 1. check valid set
    if (bufDescTable[frame].valid == false) { // frame has not been used
      std::cout << "buf " << frame << " is fresh\n";
      ready = true; 
    } else { // frame has been used
      // 2. check refbit set
      // For each frame that the clockhand goes past, the refbit is examined and then cleared
      if (bufDescTable[frame].refbit == true) { // frame has been referenced
        std::cout << "set buf " << frame << " ref clear, ready for replacement\n";
        // clear
        bufDescTable[frame].refbit = false; 
        // advance clock pointer
        advanceClock();
        frame = clockHand; // will back toward loop        
      } else { // frame has not been referenced
        // 3. check page pinned
        if (bufDescTable[frame].pinCnt >= 1) { // frame has been pin
        std::cout << "at least 1 user is using buf " << frame << "\n";
        // advance clock pointer
        advanceClock();
        frame = clockHand; // will back toward loop        
        } else {
          // 4. check dirty bit
          if (bufDescTable[frame].dirty == true) { // has been modified
            // if necessary, writing a dirty page back to disk.
            // the page is dirty and thus must be written to disk before the frame is used to hold another page
            // call file.writePage() to flush the page to disk
              // file = bufDescTable[frame].file;
              // pageNo = bufDescTable[frame].pageNo
              // index = frame;
              // page = bufPool[frame]
            bufDescTable[frame].file
              .writePage(bufDescTable[frame].pageNo, bufPool[frame]); 
            std::cout << "dirty thus flush buf "  << frame << ", write page " 
              << bufDescTable[frame].pageNo << ", " << &bufPool[frame] << ", into the disk\n";
            hashTable.remove(bufDescTable[frame].file
              , bufDescTable[frame].pageNo); 
          }           
          ready = true;     
        }
      }
    }
  }
  // frame is ready to use
  bufDescTable[frame].clear(); 

  // show
  std::cout << "allocBuf | allocate empty buf " << frame << ", ";
  bufDescTable[frame].Print();  
}

void BufMgr::readPage(File& file, const PageId pageNo, Page*& page) {
  // show
  std::cout << "readPage | reading file " << file.filename() << ", page "<< pageNo << "\n";
  // check whether the page is already in the buffer pool by invoking lookup()
  FrameId frame = 0;
  try {
    hashTable.lookup(file, pageNo, frame);
  } catch (const HashNotFoundException &) { 
    // may throw HashNotFoundException when page is not in the buffer pool

    // Case 1: Page is not in the buffer pool.
    std::cout << "cannot find page "<< pageNo << ", alloc new buf\n";
    // Call allocBuf() to allocate a buffer frame
    allocBuf(frame);
    // Call file.readPage() to read the page from disk into the buffer pool frame            
    // file.readPage(pageNo, true);
    bufPool[frame] = file.readPage(pageNo); // bufPool[frame] = new_page;
    // insert the page into the hashtable
    hashTable.insert(file, pageNo, frame);
    // invoke Set() on the frame to set it up properly
    bufDescTable[frame].Set(file, pageNo);
    // Set() will leave the pinCnt for the page set to 1

    // show
    std::cout << "init buf " << frame << ", ";
    bufDescTable[frame].Print();     
    std::cout << "\n";
    // Return a pointer to the frame containing the page via the page parameter
    // std::cout << "check page index of bufPool " << numBufs+pageNo-1 << "\n";
    // page = &(bufPool[numBufs+pageNo-1]);
    page = &(bufPool[frame]);
    // std::cout << "locate page at "<< page << " when returns\n";
    return;
  }

  // Case 2: Page is in the buffer pool. 
  std::cout << "find page "<< pageNo << " in buf " << frame << ", ";
  bufDescTable[frame].Print(); 
  // set the appropriate refbit
  bufDescTable[frame].refbit=true;
  // increment the pinCnt for the page
  bufDescTable[frame].pinCnt++;

  // show
  std::cout << "set ref & pin buf " << frame 
    // << " (frameNo=" << bufDescTable[frame].frameNo << ")" 
    << ", ";
  bufDescTable[frame].Print(); 

  // return a pointer to the frame containing the page via the page parameter
  // std::cout << "check page index of bufPool " << numBufs+pageNo-1 << "\n";
  // page = &(bufPool[numBufs+pageNo-1]);
  page = &(bufPool[frame]);
  // std::cout << "locate page at "<< page << " when returns\n";
}

void BufMgr::unPinPage(File& file, const PageId pageNo, const bool dirty) {
  // Does nothing if page is not found in the hash table lookup
  FrameId frame = 0;
  // let HashNotFoundException throw when page is not in the buffer pool
  hashTable.lookup(file, pageNo, frame);

  // try {
  //   hashTable.lookup(file, pageNo, frame);
  // } catch (const HashNotFoundException &) { 
  //   // may throw HashNotFoundException when page is not in the buffer pool
  // }

  // frame now contains file and page
  if (bufDescTable[frame].pinCnt<=0) { 
    // Throws PAGENOTPINNED if the pin count is already 0
    throw PageNotPinnedException(file.filename(), pageNo, frame);
  }

  // std::cout << "unPinPage | unpinning buf " << frame 
  //   << " / file:" << file.filename() << " page:" << pageNo << "\n";
  // Decrements the pinCnt of the frame containing (file, PageNo)
  bufDescTable[frame].pinCnt--; 

  // if dirty == true, sets the dirty bit
  if (dirty) {bufDescTable[frame].dirty=dirty;   }

  // show
  std::cout << "buf " << frame << " is unpinned, ";
  bufDescTable[frame].Print();    
}

void BufMgr::allocPage(File& file, PageId& pageNo, Page*& page) {
  // allocate an empty page in the specified file by file.allocatePage()
  Page new_page = file.allocatePage();
  // allocBuf() is called to obtain a buffer pool frame
  // At any point in time the clock hand is advanced in a clockwise fashion.
  FrameId frame = 0; // an int to assign right position
  // and a pointer to the buffer frame allocated for the page via the page parameter.
  allocBuf(frame); // test if works, if not frame would be given new vlue  

  // bufPool.push_back(new_page);
  bufPool[frame] = new_page;
  // The method returns both the page number of the newly allocated 
  // page to the caller via the pageNo parameter
  // std::cout << "check page index of bufPool " << numBufs+pageNo-1 << "\n";
  // page = &(bufPool.back());
  page = &(bufPool[frame]);
  pageNo = page->page_number();
  // show
  std::cout << "allocPage | allocate page " << pageNo << " at index " 
  // << bufPool.size()-1 << " of bufPool, " << page << "\n";
  << frame << " of bufPool, " << page << "\n";

  // an entry is inserted into the hash table
  hashTable.insert(file, pageNo, frame);
  // Set() is invoked on the frame
  bufDescTable[frame].Set(file, pageNo); 
  
  // show
  std::cout << "init buf " << frame << ", ";
  bufDescTable[frame].Print(); 

  // let HashNotFoundException throw when page is not in the buffer pool
  hashTable.lookup(file, pageNo, frame);
}

void BufMgr::flushFile(File& file) {
  // scan bufTable for pages belonging to the file
  for (int i = 0; i < numBufs; i++) {    
  // For each page encountered it should: 
    if (bufDescTable[i].file.filename() == file.filename()) {
      // Throws PagePinnedException if some page of the file is pinned. 
      if (bufDescTable[i].pinCnt>0) {
        bufDescTable[i].Print();
        throw PagePinnedException(file.filename_, bufDescTable[i].pinCnt, i);
      }
      
      // Throws BadBuffer- Exception if an invalid page belonging to the file is encountered.
      if (!bufDescTable[i].valid) {
        throw BadBufferException(i, bufDescTable[i].dirty, bufDescTable[i].valid, bufDescTable[i].refbit);
      }
      
      // (a) if the page is dirty, 
      if (bufDescTable[i].dirty) {
        // call file.writePage() to flush the page to disk and then set the dirty bit for the page to false 
        file.writePage(bufDescTable[i].pageNo, bufPool[i]);  
        std::cout << "dirty thus flush buf "  << i << ", write page " 
          << bufDescTable[i].pageNo << ", " << &bufPool[i] << ", into the disk\n";
      }
      // (b) remove the page from the hashtable (whether the page is clean or dirty) 
      hashTable.remove(bufDescTable[i].file, bufDescTable[i].pageNo); 
      // (c) invoke the Clear() method of BufDesc for the page frame.
      bufDescTable[i].clear(); 
    }
  }  
}

void BufMgr::disposePage(File& file, const PageId PageNo) {}

void BufMgr::printSelf(void) {
  int validFrames = 0;

  for (FrameId i = 0; i < numBufs; i++) {
    std::cout << "FrameNo:" << i << " ";
    bufDescTable[i].Print();

    if (bufDescTable[i].valid) validFrames++;
  }

  std::cout << "Total Number of Valid Frames:" << validFrames << "\n";
}

}  // namespace badgerdb
