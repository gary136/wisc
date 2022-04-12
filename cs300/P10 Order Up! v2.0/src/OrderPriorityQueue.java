//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define OrderPriorityQueue
// Course: CS 300 Spring 2021
//
// Author: Lee Hung Ting
// Email: hlee864@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A max-heap implementation of a priority queue for Orders, where the Order with the LONGEST prep 
 * time is returned first instead of the strict first-in-first-out queue as in P08.
 * 
 * TODO: Make sure Order implements Comparable<Order> so that this class can implement the
 * PriorityQueueADT without error!
 */
public class OrderPriorityQueue implements PriorityQueueADT<Order>{

  // Data fields; do not modify
  private Order[] queueHeap;
  private int size;
  
  /**
   * Constructs a PriorityQueue for Orders with the given capacity
   * 
   * @param capacity the initial capacity for the queue
   * @throws IllegalArgumentException if the given capacity is 0 or negative
   */
  public OrderPriorityQueue(int capacity) throws IllegalArgumentException{
    // TODO throw IllegalArgumentException if capacity is invalid
    if (capacity<=0)
      throw new IllegalArgumentException("the argument is invalid");
    // TODO initialize data fields appropriately
    this.queueHeap = new Order[capacity];
  }
  
  /**
   * Inserts a new Order into the queue in the appropriate position using a heap's add logic.
   * 
   * @param newOrder the Order to be added to the queue
   */
  @Override
  public void insert(Order newOrder) {
    // TODO If the queue is empty, insert the new order at the root of the heap
    if (this.size==0) {
      this.queueHeap[0] = newOrder;
      this.size++;
    }
    
    // TODO If the queue is FULL, create a new Order array of double the current heap's size,
    // copy all elements of the current heap over and update the queueHeap reference
    // -> HINT: use Arrays.copyOf(), copying arrays is not the point of this assignment
    else {
      if (this.size==this.queueHeap.length) {
        this.queueHeap = Arrays.copyOf(this.queueHeap, 2*this.queueHeap.length);
      }
      
      // TODO add the newOrder to the end of the heap and percolate up to ensure 
      // a valid heap, where the Order with the LONGEST prep time is at the root of the heap
      this.queueHeap[this.size] = newOrder;
      this.size++;
      percolateUp(this.queueHeap, this.size-1);
    }
  }
  
  /**
   * A utility method to percolate Order values UP through the heap; see figure 13.3.1 in zyBooks
   * for a pseudocode algorithm.
   * 
   * @param heap an array containing the Order values to be percolated into a valid heap
   * @param orderIndex the index of the Order to be percolated up
   */
  protected static void percolateUp(Order[] heap, int orderIndex) {
    // TODO implement the max heap percolate up algorithm to modify the heap in-place
    int child = orderIndex;
    int parent;
    while (child > 0) { // not reach root
      parent = (child-1) / 2;
      // if heapified then cease
      if (heap[parent].compareTo(heap[child])>=0) break;
      // swap
      Order temp = heap[parent];
      heap[parent] = heap[child];
      heap[child] = temp;
      child = parent;
    }
  }
  
  /**
   * Return the Order with the longest prep time from the queue and adjust the queue accordingly
   * 
   * @return the Order with the current longest prep time from the queue
   * @throws NoSuchElementException if the queue is empty
   */
  @Override
  public Order removeBest() throws NoSuchElementException{
    // TODO If the queue is empty, throw a NoSuchElementException
    if (this.size==0)
      throw new NoSuchElementException("queue has no element");
    
    // TODO Remove the root Order of the heap and re-structure 
    // the heap to ensure that its ordering is valid, then return the previous root
    Order res = this.queueHeap[0];
//    System.out.println("heap = " + this);
//    System.out.println("pop " + res);
    
    if (this.size==1) {
     this.queueHeap[0] = null;
     this.size--;
     return res; 
    }
    
    // copy the last element into the root 
    this.queueHeap[0] = this.queueHeap[this.size-1];
//    System.out.println(this.queueHeap[0]);
//    System.out.println("remove " + this.queueHeap[this.size-1]);

    // delete the last and copy the rest into another array
    Order[] proxyArray = new Order[this.queueHeap.length - 1]; 
    for (int i = 0; i < this.size-1; i++) { // leave the last 
        proxyArray[i] = this.queueHeap[i]; 
    } 

//    for (int i = 0; i < this.size-1; i++) { // leave the last 
//      System.out.print(proxyArray[i] + " -> ");
//    } 
    this.size--;
    this.queueHeap = proxyArray;
//    System.out.println("before percolateDown heap = " + this);
    
    percolateDown(this.queueHeap, 0, this.size);
//    System.out.println("after percolateDown heap = " + this);
    return res; 
  }
  
  /**
   * A utility method to percolate Order values DOWN through the heap; see figure 13.3.2 in zyBooks
   * for a pseudocode algorithm.
   * 
   * @param heap an array containing the Order values to be percolated into a valid heap
   * @param orderIndex the index of the Order to be percolated down
   * @param size the number of initialized elements in the heap
   */
  protected static void percolateDown(Order[] heap, int orderIndex, int size) {
    // TODO implement the max heap percolate down algorithm to modify the heap in-place
    int childIndex = 2 * orderIndex + 1;
    Order currOrder = heap[orderIndex];

    while (childIndex < size) {
       // Find the max among the node and all the node's children
       Order maxOrder = currOrder;
       int maxIndex = -1;
       for (int i = 0; i < 2 && i + childIndex < size; i++) {
          // see parent and 2 children as a group to compare
          if (heap[i + childIndex].compareTo(maxOrder) > 0) {
             maxOrder = heap[i + childIndex];
             maxIndex = i + childIndex;
          }
       }
  
       if (currOrder.compareTo(maxOrder) == 0) break;
       // need not to perculateDown
       
       else {
          // swap
          Order temp = heap[orderIndex];
          heap[orderIndex] = heap[maxIndex];
          heap[maxIndex] = temp;
          orderIndex = maxIndex;
          // move to next
          childIndex = 2 * orderIndex + 1;
       }
    }
  }
  
  /**
   * Return the Order with the highest prep time from the queue without altering the queue
   * @return the Order with the current longest prep time from the queue
   * @throws NoSuchElementException if the queue is empty
   */
  @Override
  public Order peekBest() throws NoSuchElementException {
    // TODO If the queue is empty, throw a NoSuchElementException
    if (this.size==0)
      throw new NoSuchElementException("queue has no element");
    
    // TODO Return the Order with the longest prep time currently in the queue
    return this.queueHeap[0]; // max = root
  }
  
  /**
   * Returns true if the queue contains no Orders, false otherwise
   * @return true if the queue contains no Orders, false otherwise
   */
  @Override
  public boolean isEmpty() {
    // TODO implement this method according to its javadoc comment
    if (this.size==0) return true;
    return false; // included to prevent compiler errors
  }
  
  /**
   * Returns the number of elements currently in the queue
   * @return the number of elements currently in the queue
   */
  public int size() {
    // TODO implement this method according to its javadoc comment
    return this.size; // included to prevent compiler errors
  }
  
  /**
   * Creates a String representation of this PriorityQueue. Do not modify this implementation; this
   * is the version that will be used by all provided OrderPriorityQueue implementations that your
   * tester code will be run against.
   * 
   * @return the String representation of this PriorityQueue, primarily for testing purposes
   */
  public String toString() {
    String toReturn = "";
    for (int i=0; i < this.size; i++) {
      toReturn += queueHeap[i].getID()+"("+queueHeap[i].getPrepTime()+")";
      if (i < this.size-1) toReturn += ", ";
    }
    return toReturn;
  }
  
}
