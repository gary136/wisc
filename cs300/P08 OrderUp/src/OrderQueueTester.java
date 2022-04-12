import java.util.NoSuchElementException;

//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define OrderQueueTester
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

/**
 * Wrapper for the Order objects, so that they can be queued in a LinkedList format
 */
public class OrderQueueTester {
  
  /**
   * test whether OrderIterator is correct
   * 
   * @return true if and only if OrderIterator is correct
   */
  public static boolean OrderIteratorTests(){
    // Create a chain of LinkedOrders using the provided 
    // LinkedOrder constructor and mutator method
    
    LinkedOrder lnkO = new LinkedOrder(new Order("steak", 5));
    LinkedOrder lnk1 = new LinkedOrder(new Order("cake", 1));
    LinkedOrder lnk2 = new LinkedOrder(new Order("bread", 4));
    LinkedOrder lnk3 = new LinkedOrder(new Order("cheesecake", 30));
    
    lnkO.setNext(lnk1);
    lnk1.setNext(lnk2);
    lnk2.setNext(lnk3);
    
    // create an OrderIterator with the first LinkedOrder in the chain 
    // and verify that these methods work as you expect
    
    OrderIterator oIterator = new OrderIterator(lnkO);
    if (!oIterator.hasNext()) {
      System.out.println("hasNext broken");
      return false;
    }
    
    // demo
    String dish = "";
    while (oIterator.hasNext()) {
      dish = oIterator.next().getDishName();
      System.out.print(dish + " -> ");
    }
    
    if (dish!="cheesecake"){
      System.out.println("next broken");
      return false;
    }
    
    try {oIterator.next();}
    catch (NoSuchElementException excpt) {
      System.out.println(excpt.getMessage());
    }
    
    Order.resetIDGenerator();
    System.out.println("OrderIteratorTests end\n");
    return true;
  }

  /**
   * test whether OrderQueue - enqueue is correct
   * 
   * @return true if and only if OrderQueue - enqueue is correct
   */
  public static boolean OrderQueueEnqueueTests(){
    OrderQueue oQ = new OrderQueue();
    try { oQ.peek();}// check exception would be triggered    
    catch (NoSuchElementException excpt) {
      System.out.println(excpt.getMessage());
    }
    
    oQ.enqueue(new Order("steak", 5)); 
    if (oQ.peek().getDishName()!="steak") {
        System.out.println("peek broken");
        return false;
      } // check the right order is added
    System.out.println(oQ);
    if (!oQ.iterator().hasNext()) {
      System.out.println("hasNext broken");
      return false;
    } // check iterator has next
    
    oQ.enqueue(new Order("cake", 1));
    System.out.println(oQ);
    oQ.enqueue(new Order("bread", 4)); 
    System.out.println(oQ);
    
    if (!oQ.toString()
      .equals("1001: steak (5) -> 1002: cake (1) -> 1003: bread (4) -> END")) {
      System.out.println("toString broken");
      return false;
    } // check the right sequence
    
    Order.resetIDGenerator();
    System.out.println("OrderQueueEnqueueTests end\n");
    return true;
  }
  
  /**
   * test whether OrderQueue - Dequeue is correct
   * 
   * @return true if and only if OrderQueue - Dequeue is correct
   */
  public static boolean OrderQueueDequeueTests(){
    OrderQueue oQ = new OrderQueue();
    
    try { // check exception would be triggered
      oQ.dequeue();
    }
    catch (NoSuchElementException excpt) {
      System.out.println(excpt.getMessage());
    }
    
    oQ.enqueue(new Order("steak", 5));
    oQ.enqueue(new Order("cake", 1));
    oQ.enqueue(new Order("bread", 4)); 
    
    Order removal = oQ.dequeue();
    System.out.println(oQ);
    if (removal.getDishName()!="steak") {
      System.out.println("dequeue broken");
      return false;
    }
    
    if (oQ.isEmpty()) {
      System.out.println("dequeue broken");
      return false;
    }
    
    if (oQ.peek().getDishName()!="cake") {
      System.out.println("dequeue broken");
      return false;
    }    
    
    oQ.dequeue();
    System.out.println(oQ);
    oQ.dequeue();
    if (!oQ.isEmpty()) {
      System.out.println("dequeue broken");
      return false;
    }
    System.out.println("all elements are gone");
    
    Order.resetIDGenerator();
    System.out.println("OrderQueueDequeueTests end\n");
    return true;
  }
  
//  (-1.0): Checks whether your OrderQueue toString method works correctly.
//  Problem detected! Your OrderQueue's toString() method did not contain the Order at index 0 of a 3-element queue.
//  Expected: 1001: burger (5)
//  in String: 1002: fries (2) -> 1003: pizza (8) -> END
//  (-1.0): Checks whether your OrderQueue toString method works correctly.
//  Problem detected! Expected your OrderQueue to return the String 
//  1001: burger (5) -> 1002: fries (2) -> 1003: pizza (8) -> END
//  but it returned
//  1002: fries (2) -> 1003: pizza (8) -> END
//  Verify that your formatting and whitespace is correct, and that the contents of the queue are all present in the correct order.

  /**
   * test whether OrderQueue - Print is correct
   * 
   * @return true if and only if OrderQueue - Print is correct
   */
  public static boolean OrderQueuePrintTests(){
    OrderQueue oQ = new OrderQueue();

    oQ.enqueue(new Order("burger", 5)); 
    System.out.println(oQ.peek());
    System.out.println(oQ);
    oQ.enqueue(new Order("fries", 2));
    System.out.println(oQ);
    oQ.enqueue(new Order("pizza", 8)); 
    System.out.println(oQ);
    
    if (!oQ.toString()
        .equals("1001: burger (5) -> 1002: fries (2) -> 1003: pizza (8) -> END"))
      return false;
    
    Order.resetIDGenerator();
    System.out.println("OrderQueuePrintTests end\n");
    return true;
  }
  
  /**
   * check the result of tests
   * 
   * @return true if and only if all tests return true
   */
  public static boolean runAllTests(){
    boolean t1 = OrderIteratorTests(); 
    if (!t1) {
      System.out.println("OrderIteratorTests fail") ;
      return false;
    }
    boolean t2 = OrderQueueEnqueueTests();
    if (!t2) {
      System.out.println("OrderQueueEnqueueTests fail") ;
      return false;
    }
    boolean t3 = OrderQueueDequeueTests();
    if (!t3) {
      System.out.println("OrderQueueDequeueTests fail") ;
      return false;
    }
    boolean t4 = OrderQueuePrintTests();
    if (!t4) {
      System.out.println("OrderQueuePrintTests fail") ;
      return false;
    }
    return true;
  }
  
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println(runAllTests());
  }

}
