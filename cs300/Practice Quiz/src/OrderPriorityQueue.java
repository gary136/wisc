//// Remember to add your file header here
//
//import java.util.Arrays;
//
///**
// * A max-heap implementation of a priority queue for Orders, where the Order with the LONGEST prep 
// * time is returned first instead of the strict first-in-first-out queue as in P08.
// * 
// * TODO: Make sure Order implements Comparable<Order> so that this class can implement the
// * PriorityQueueADT without error!
// */
//public class OrderPriorityQueue implements PriorityQueueADT<Order>{
//
//  // Data fields; do not modify
//  private Order[] queueHeap;
//  private int size;
//  
//  /**
//   * Constructs a PriorityQueue for Orders with the given capacity
//   * 
//   * @param capacity the initial capacity for the queue
//   * @throws IllegalArgumentException if the given capacity is 0 or negative
//   */
//  public OrderPriorityQueue(int capacity) {
//    // TODO throw IllegalArgumentException if capacity is invalid
//    
//    // TODO initialize data fields appropriately
//  }
//  
//  /**
//   * Inserts a new Order into the queue in the appropriate position using a heap's add logic.
//   * 
//   * @param newOrder the Order to be added to the queue
//   */
//  @Override
//  public void insert(Order newOrder) {
//    // TODO If the queue is empty, insert the new order at the root of the heap
//    
//    // TODO If the queue is FULL, create a new Order array of double the current heap's size,
//    // copy all elements of the current heap over and update the queueHeap reference
//    // -> HINT: use Arrays.copyOf(), copying arrays is not the point of this assignment
//    
//    // TODO add the newOrder to the end of the heap and percolate up to ensure a valid heap, where
//    // the Order with the LONGEST prep time is at the root of the heap
//  }
//  
//  /**
//   * A utility method to percolate Order values UP through the heap; see figure 13.3.1 in zyBooks
//   * for a pseudocode algorithm.
//   * 
//   * @param heap an array containing the Order values to be percolated into a valid heap
//   * @param orderIndex the index of the Order to be percolated up
//   */
//  protected static void percolateUp(Order[] heap, int orderIndex) {
//    // TODO implement the max heap percolate up algorithm to modify the heap in-place
//  }
//  
//  /**
//   * Return the Order with the longest prep time from the queue and adjust the queue accordingly
//   * 
//   * @return the Order with the current longest prep time from the queue
//   * @throws NoSuchElementException if the queue is empty
//   */
//  @Override
//  public Order removeBest() {
//    // TODO If the queue is empty, throw a NoSuchElementException
//    
//    // TODO Remove the root Order of the heap and re-structure the heap to ensure that its ordering
//    // is valid, then return the previous root
//    
//    return null; // included to prevent compiler errors
//  }
//  
//  /**
//   * A utility method to percolate Order values DOWN through the heap; see figure 13.3.2 in zyBooks
//   * for a pseudocode algorithm.
//   * 
//   * @param heap an array containing the Order values to be percolated into a valid heap
//   * @param orderIndex the index of the Order to be percolated down
//   * @param size the number of initialized elements in the heap
//   */
//  protected static void percolateDown(Order[] heap, int orderIndex, int size) {
//    // TODO implement the max heap percolate down algorithm to modify the heap in-place
//  }
//  
//  /**
//   * Return the Order with the highest prep time from the queue without altering the queue
//   * @return the Order with the current longest prep time from the queue
//   * @throws NoSuchElementException if the queue is empty
//   */
//  @Override
//  public Order peekBest() {
//    // TODO If the queue is empty, throw a NoSuchElementException
//    
//    // TODO Return the Order with the longest prep time currently in the queue
//    return null; // included to prevent compiler errors
//  }
//  
//  /**
//   * Returns true if the queue contains no Orders, false otherwise
//   * @return true if the queue contains no Orders, false otherwise
//   */
//  @Override
//  public boolean isEmpty() {
//    // TODO implement this method according to its javadoc comment
//    return false; // included to prevent compiler errors
//  }
//  
//  /**
//   * Returns the number of elements currently in the queue
//   * @return the number of elements currently in the queue
//   */
//  public int size() {
//    // TODO implement this method according to its javadoc comment
//    return -1; // included to prevent compiler errors
//  }
//  
//  /**
//   * Creates a String representation of this PriorityQueue. Do not modify this implementation; this
//   * is the version that will be used by all provided OrderPriorityQueue implementations that your
//   * tester code will be run against.
//   * 
//   * @return the String representation of this PriorityQueue, primarily for testing purposes
//   */
//  public String toString() {
//    String toReturn = "";
//    for (int i=0; i < this.size; i++) {
//      toReturn += queueHeap[i].getID()+"("+queueHeap[i].getPrepTime()+")";
//      if (i < this.size-1) toReturn += ", ";
//    }
//    return toReturn;
//  }
//  
//}
