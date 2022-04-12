// --== CS400 File Header Information ==--
// Name: Lee Hung Ting
// Email: hlee864@@wisc.edu
// Notes to Grader: None

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Red-Black Tree implementation with a Node inner class for representing
 * the nodes of the tree. Currently, this implements a Binary Search Tree that
 * we will turn into a red black tree by modifying the insert functionality.
 * In this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a level-order
 * traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree
     * the parent, left, and right child references are always maintained.
     */
    protected static class Node<T> {
        public T data;
        public Node<T> parent; // null for root node
        public Node<T> leftChild; 
        public Node<T> rightChild; 
        
        // 1. Add a public boolean isBlack field to the Node inner class
        public boolean isBlack; // Ensure that every newly instantiated Node object 
                                // has a false isBlack field 
                                // (so that all new nodes are red by default)
        
        // constructor
        public Node(T data) { 
          this.data = data; 
          this.isBlack = false;
        }
        
        /**
         * @return true when this node has a parent and is the left child of
         * that parent, otherwise return false
         */
        public boolean isLeftChild() {
            return parent != null && parent.leftChild == this;
        }
        /**
         * This method performs a level order traversal of the tree rooted
         * at the current node. The string representations of each data value
         * within this tree are assembled into a comma separated string within
         * brackets (similar to many implementations of java.util.Collection).
         * Note that the Node's implementation of toString generates a level
         * order traversal. The toString of the RedBlackTree class below
         * produces an inorder traversal of the nodes / values of the tree.
         * This method will be helpful as a helper for the debugging and testing
         * of your rotation implementation.
         * @return string containing the values of this tree in level order
         */
        @Override
        public String toString() {
            String output = "[";
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.leftChild != null) q.add(next.leftChild);
                if(next.rightChild != null) q.add(next.rightChild);
                output += next.data.toString();
//                if (next.isBlack) {
//                  output += "|B";
//                } else {
//                  output += "|R";
//                };
                if(!q.isEmpty()) output += ", ";
            }
            return output + "]";
        }
    }
    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree
    
    /**
     * Performs a naive insertion into a binary search tree: adding the input
     * data value to a new node in a leaf position within the tree. After  
     * this insertion, no attempt is made to restructure or balance the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if not
     * @throws NullPointerException when the provided data argument is null
     * @throws IllegalArgumentException when the newNode and subtree contain
     *      equal data references
     */
    @Override
    public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
        // null references cannot be stored within this tree
        if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");
        // 2. At the end of the insert method, set the root node of to be black
        Node<T> newNode = new Node<>(data);
//        System.out.println("newNode = " + newNode);
        if(root == null) { 
//          System.out.println("add empty node");
          root = newNode; 
          size++; 
          // if x is the root, change the color of x as BLACK
          this.root.isBlack = true; 
          return true; 
        } // add first node to an empty tree
        else{
//          System.out.println("add existing node");
            boolean returnValue = insertHelper(newNode,root); 
            // recursively insert into subtree
            if (returnValue) {
              size++; 
              if (newNode.parent.parent!=null)
                enforceRBTreePropertiesAfterInsert(newNode);
              this.root.isBlack = true;
            }
            else throw new IllegalArgumentException(
	    	"This RedBlackTree already contains that value.");
            return returnValue;
        }
    }

    /** 
     * Recursive helper method to find the subtree with a null reference in the
     * position that the newNode should be inserted, and then extend this tree
     * by the newNode in that position.
     * @param newNode is the new node that is being added to this tree
     * @param subtree is the reference to a node within this tree which the 
     *      newNode should be inserted as a descenedent beneath
     * @return true is the value was inserted in subtree, false if not
     */
    private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
        // curr = subtree
        int compare = newNode.data.compareTo(subtree.data);
        // do not allow duplicate values to be stored within this tree
        if(compare == 0) return false;

        // store newNode within left subtree of subtree
        else if(compare < 0) { // newNode < curr
//            System.out.println("curr = " + subtree);
//            System.out.println("turn left");
            // The enforceRBTreePropertiesAfterInsert method should be called 
            // from insertHelper after adding a new red node to the tree 
            // (in both the cases of adding this new node as a left child 
            // and as a right child)
            
            if(subtree.leftChild == null) { // left subtree empty, add here
                subtree.leftChild = newNode;
                newNode.parent = subtree;
                
//                if (newNode.parent.parent!=null)
//                enforceRBTreePropertiesAfterInsert(newNode);
                
                return true;
            // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.leftChild);
        }

        // store newNode within the right subtree of subtree
        else { // newNode > curr
//            System.out.println("curr = " + subtree);
//            System.out.println("turn right");
            if(subtree.rightChild == null) { // right subtree empty, add here
                subtree.rightChild = newNode;
                newNode.parent = subtree;
                
//                if (newNode.parent.parent!=null)                  
//                enforceRBTreePropertiesAfterInsert(newNode);
                
                return true;
            // otherwise continue recursive search for location to insert
            } else return insertHelper(newNode, subtree.rightChild);
        }
    }
    
    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a leftChild of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * rightChild of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        // TODO: Implement this method.
      if (parent.leftChild==child) { // right rotation
        parent.leftChild = child.rightChild;
        if (child.rightChild != null) {
            child.rightChild.parent = parent;
        }
        
        child.parent = parent.parent;
        if (parent.parent == null) {
            this.root = child;
        } else if (parent == parent.parent.rightChild) {
            parent.parent.rightChild = child;
        } else {
            parent.parent.leftChild = child;
        }
        
        child.rightChild = parent;
        parent.parent = child;
      }
      
      if (parent.rightChild==child) { // left rotation
        parent.rightChild = child.leftChild;
        if (child.leftChild != null) {
            child.leftChild.parent = parent;
        }
        child.parent = parent.parent;
        if (parent.parent == null) {
            this.root = child;
        } else if (parent == parent.parent.leftChild) {
            parent.parent.leftChild = child;
        } else {
            parent.parent.rightChild = child;
        }
        child.leftChild = parent;
        parent.parent = child;
      }
      root.isBlack = true;
    }
  
    /**
     * a newly added red node as its only parameter. 
     * this method may also be called recursively, in which case the input parameter 
     * may reference a different red node in the tree that potentially has a red parent node. 
     * The job of this enforceRBTreePropertiesAfterInsert method is 
     * to resolve a red child under a red parent red black tree property 
     * violations that are introduced by inserting new nodes into a red black tree. 
     * While doing so, all other red black tree properties must also be preserved.
     */    
    
    private void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
//      System.out.println("Before the rebalance, Level order = " + this.root + "\n");
      
      Node u; // uncle
      while (!newNode.parent.isBlack) {
          if (newNode.parent == newNode.parent.parent.rightChild) {
              u = newNode.parent.parent.leftChild; // uncle
              if (u!=null && !u.isBlack) { // uncle is red
                  // change the color of the node’s parent and uncle to black
                  u.isBlack = true;
                  newNode.parent.isBlack = true;
                  // that of grandfather to red
                  newNode.parent.parent.isBlack = false;
                  // newNode = newNode.parent.parent;
              } 
              else { // uncle is null or black
                  if (newNode == newNode.parent.leftChild) {
                      // Right Left Case (RL rotation)
                      // newNode = newNode.parent;
                      // rightRotate(newNode);

                      // right rotate at P
                      rotate(newNode, newNode.parent);
                      // then RR rotation
                  }
                  
                  // Right Right Case (RR rotation)
                  newNode.parent.isBlack = true;
                  newNode.parent.parent.isBlack = false;
                  // leftRotate(newNode.parent.parent);

                  // left rotate at G
                  // swap the color of G and P
                  // G|B, P|R -> G|R, P|B 
                  rotate(newNode.parent, newNode.parent.parent);
              }
          } 
          else {
              u = newNode.parent.parent.rightChild; // uncle

              if (!u.isBlack) { // mirror case
                  u.isBlack = true;
                  newNode.parent.isBlack = true;
                  newNode.parent.parent.isBlack = false;
                  // newNode = newNode.parent.parent; 
              } 
              else {
                  if (newNode == newNode.parent.rightChild) {
                      // Left Left Case (LL rotation)
                      // newNode = newNode.parent;
                      // leftRotate(newNode);

                      // left rotate at P
                      rotate(newNode, newNode.parent);
                      // then LR rotation
                  }
                  
                  // Left Right Case (LR rotation)
                  newNode.parent.isBlack = true;
                  newNode.parent.parent.isBlack = false;
                  // rightRotate(newNode.parent.parent);
                  
                  // right rotate at G
                  // swap the color of G and P
                  // G|B, P|R -> G|R, P|B 
                  rotate(newNode.parent, newNode.parent.parent);
              }
          }
          if (newNode == root) {
              break;
          }
      }
      root.isBlack = true;
//      System.out.println("After the rebalance, Level order = " + this.root + "\n");
    }
    /**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() return 0, false if this.size() > 0
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data the data value to test for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    @Override
    public boolean contains(T data) {
        // null references will not be stored within this tree
        if(data == null) throw new NullPointerException(
            "This RedBlackTree cannot store null references.");
        return this.containsHelper(data, root);
    }

    /**
     * Recursive helper method that recurses through the tree and looks
     * for the value *data*.
     * @param data the data value to look for
     * @param subtree the subtree to search through
     * @return true of the value is in the subtree, false if not
     */
    private boolean containsHelper(T data, Node<T> subtree) {
        if (subtree == null) {
            // we are at a null child, value is not in tree
            return false;
        } else {
            int compare = data.compareTo(subtree.data);
            if (compare < 0) {
                // go left in the tree
                return containsHelper(data, subtree.leftChild);
            } else if (compare > 0) {
                // go right in the tree
                return containsHelper(data, subtree.rightChild);
            } else {
                // we found it :)
                return true;
            }
        }
    }

    /**
     * Returns an iterator over the values in in-order (sorted) order.
     * @return iterator object that traverses the tree in in-order sequence
     */
    @Override
    public Iterator<T> iterator() {
        // use an anonymous class here that implements the Iterator interface
        // we create a new on-off object of this class everytime the iterator
        // method is called
        return new Iterator<T>() {
            // a stack and current reference store the progress of the traversal
            // so that we can return one value at a time with the Iterator
            Stack<Node<T>> stack = null;
            Node<T> current = root;

            /**
             * The next method is called for each value in the traversal sequence.
             * It returns one value at a time.
             * @return next value in the sequence of the traversal
             * @throws NoSuchElementException if there is no more elements in the sequence
             */
            public T next() {
                // if stack == null, we need to initialize the stack and current element
                if (stack == null) {
                    stack = new Stack<Node<T>>();
                    current = root;
                }
                // go left as far as possible in the sub tree we are in until we hit a null
                // leaf (current is null), pushing all the nodes we fund on our way onto the
                // stack to process later
                while (current != null) {
                    stack.push(current);
                    current = current.leftChild;
                }
                // as long as the stack is not empty, we haven't finished the traversal yet;
                // take the next element from the stack and return it, then start to step down
                // its right subtree (set its right sub tree to current)
                if (!stack.isEmpty()) {
                    Node<T> processedNode = stack.pop();
                    current = processedNode.rightChild;
                    return processedNode.data;
                } else {
                    // if the stack is empty, we are done with our traversal
                    throw new NoSuchElementException("There are no more elements in the tree");
                }
            }

            /**
             * Returns a boolean that indicates if the iterator has more elements (true),
             * or if the traversal has finished (false)
             * @return boolean indicating whether there are more elements / steps for the traversal
             */
            public boolean hasNext() {
                // return true if we either still have a current reference, or the stack
                // is not empty yet
                return !(current == null && (stack == null || stack.isEmpty()) );
            }            
        };
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * Note that this RedBlackTree class implementation of toString generates an
     * inorder traversal. The toString of the Node class class above
     * produces a level order traversal of the nodes / values of the tree.
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    @Override
    public String toString() {
        // use the inorder Iterator that we get by calling the iterator method above
        // to generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        Iterator<T> treeNodeIterator = this.iterator();
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (treeNodeIterator.hasNext())
            sb.append(treeNodeIterator.next());
        while (treeNodeIterator.hasNext()) {
            T data = treeNodeIterator.next();
            sb.append(", ");
            sb.append(data.toString());
        }
        sb.append(" ]");
        return sb.toString();
    }
    
//    @Test
//    public void week05ActivityTestPart1() {
//      RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
//      tree.root = new Node<Integer>(28);
//      System.out.println("Level order = " + tree.root + "\n");
//      tree.insert(80);
//      System.out.println("Level order = " + tree.root + "\n");
//      tree.insert(86);
////      tree.rotate(tree.root.rightChild, tree.root);
//      tree.insert(97);
//      tree.insert(33);
////      assertEquals(tree.root.toString(), "[80, 28, 86, 33, 97]"); 
//    }    
//    public static void main(String[] args) {
//      RedBlackTree test1 = new RedBlackTree();
//      test1.week05ActivityTestPart1();
//      
////      RedBlackTree test2 = new RedBlackTree();
////      test2.week05ActivityTestPart2();
////      
////      RedBlackTree test3 = new RedBlackTree();
////      test3.week05ActivityTestPart3();     
//
//    }
}