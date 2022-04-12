////////////////FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
//Title: define OrderPriorityQueueTester
//Course: CS 300 Spring 2021
//
//Author: Lee Hung Ting
//Email: hlee864@wisc.edu
//Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
//Persons: None
//Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * This class checks the correctness of the implementation of the methods defined in the class
 * OrderPriorityQueue.
 * 
 * You MAY add additional public static boolean methods to this class if you like, and any private
 * static helper methods you need.
 */
public class OrderPriorityQueueTester {
  
  /**
   * Checks the correctness of the isEmpty method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue and verify that it is empty
   * (2) add a new Order to the queue and verify that it is NOT empty
   * (3) remove that Order from the queue and verify that it is empty again
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testIsEmpty() {
    Order.resetIDGenerator();
    
    // TODO implement scenario 1, then go write the constructor and isEmpty methods in your
    // OrderPriorityQueue class so that they pass the tests
    try {
      OrderPriorityQueue opq = new OrderPriorityQueue(5);
      if (!opq.isEmpty()) {
        System.out.println("isEmpty fails!");
        return false;
      }
      // TODO implement scenario 2, then go write enough of insert() to pass the tests
      opq.insert(new Order("steak", 5));
      if (opq.isEmpty()) {
        System.out.println("isEmpty fails!");
        return false;
      }
      // TODO implement scenario 3, then go write enough of remove() to pass the tests
      opq.removeBest();
      if (!opq.isEmpty()) {
        System.out.println("isEmpty fails!");
        return false;
      }
    }    
    catch (Exception e) { // an unexpected exception was thrown
      e.printStackTrace();
      return false;
    }
    return true; // included to prevent compiler errors
  }
  
  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue and add a single order with a large prepTime to it
   * (2) use the OrderPriorityQueue toString method to verify that the queue's internal structure
   *     is a valid heap
   * (3) add at least three more orders with DECREASING prepTimes to the queue and repeat step 2.
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testInsertBasic() {
    Order.resetIDGenerator();
    
    // TODO implement this method, then go write the insert method in your
    // OrderPriorityQueue class so that it passes this test method
    try {
      OrderPriorityQueue opq = new OrderPriorityQueue(5);
      opq.insert(new Order("steak", 500));
      if (!opq.toString().equals("1001(500)")){
        System.out.println("insert fails!");
        return false;
      }
      opq.insert(new Order("cheesecake", 30));
      opq.insert(new Order("bread", 4));
      opq.insert(new Order("cake", 1));
      if (!opq.toString().equals("1001(500), 1002(30), 1003(4), 1004(1)")){
        System.out.println("insert fails!");
        return false;
      }
    }
    catch (Exception e) { // an unexpected exception was thrown
      e.printStackTrace();
      return false;
    }
    return true; // included to prevent compiler errors
  }
  
  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create an array of at least four Orders that represents a valid heap
   * (2) add a fifth order at the next available index that is NOT in a valid heap position
   * (3) pass this array to OrderPriorityQueue.percolateUp()
   * (4) verify that the resulting array is a valid heap
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testPercolateUp() {
    Order.resetIDGenerator();
    
    // TODO implement this method, then go write the percolateUp() method in your
    // OrderPriorityQueue class so that it passes this test method
    try {
      OrderPriorityQueue opq = new OrderPriorityQueue(5);
      opq.insert(new Order("steak", 500));
      opq.insert(new Order("cheesecake", 30));
      opq.insert(new Order("bread", 4));
      opq.insert(new Order("cake", 1));
      opq.insert(new Order("salad", 80));
      if (!opq.toString().equals("1001(500), 1005(80), 1003(4), 1004(1), 1002(30)")){
        System.out.println("insert fails!");
        return false;
      }
    }
    catch (Exception e) { // an unexpected exception was thrown
      e.printStackTrace();
      return false;
    }
    return true; // included to prevent compiler errors
  }
  
  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue with at least 6 orders of varying prepTimes, adding them
   *     to the queue OUT of order
   * (2) use the OrderPriorityQueue toString method to verify that the queue's internal structure
   *     is a valid heap
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testInsertAdvanced() {
    Order.resetIDGenerator();
    
    // TODO implement this method, then go modify the insert method in your
    // OrderPriorityQueue class so that it passes this test method too
    try {
      OrderPriorityQueue opq = new OrderPriorityQueue(10);
      opq.insert(new Order("bread", 4));
      opq.insert(new Order("steak", 500));
      opq.insert(new Order("salad", 80));
      opq.insert(new Order("cake", 1));
      opq.insert(new Order("cheesecake", 30));
      opq.insert(new Order("tea", 400));
      if (!opq.toString().equals("1002(500), 1005(30), 1006(400), 1004(1), 1001(4), 1003(80)")){
        System.out.println("insert fails!");
        return false;
      }
    }
    catch (Exception e) { // an unexpected exception was thrown
      e.printStackTrace();
      return false;
    }
    return true; // included to prevent compiler errors
  }
  
  /**
   * Checks the correctness of the insert method of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create an array of at least five Orders where the Order 
   *     at index 0 is NOT in valid heap position
   * (2) pass this array to OrderPriorityQueue.percolateDown()
   * (3) verify that the resulting array is a valid heap
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testPercolateDown() {
    Order.resetIDGenerator();
    
    // TODO implement this method, then go write the percolateUp() method in your
    // OrderPriorityQueue class so that it passes this test method
//    OrderPriorityQueue opq = new OrderPriorityQueue(10);
    Order[] opq = new Order[10];
    try {
      opq[0] = new Order("bread", 4);
      opq[1] = new Order("cheesecake", 30);
      opq[2] = new Order("steak", 500);
      opq[3] = new Order("cake", 1);
      opq[4] = new Order("salad", 80);
    //    opq.insert(new Order("bread", 4));
    //    opq.insert(new Order("cheesecake", 30));
    //    opq.insert(new Order("steak", 500));
    //    opq.insert(new Order("cake", 1));
    //    opq.insert(new Order("salad", 80));
      System.out.println(opq[2]);
    //    System.out.println(opq.peekBest());
      OrderPriorityQueue.percolateDown(opq, 0, 4);
      
      if (!opq[0].toString().equals("1003: steak (500)")){
        System.out.println("PercolateDown fails!");
        return false;
      }    
    }
    catch (Exception e) { // an unexpected exception was thrown
      e.printStackTrace();
      return false;
    }
    return true; // included to prevent compiler errors
  }
  
  /**
   * Checks the correctness of the removeBest and peekBest methods of OrderPriorityQueue.
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue with at least 6 orders of varying prepTimes, adding them
   *     to the queue in whatever order you like
   * (2) remove all but one of the orders, verifying that each order has a SHORTER prepTime than
   *     the previously-removed order
   * (3) peek to see that the only order left is the one with the SHORTEST prepTime
   * (4) check isEmpty to verify that the queue has NOT been emptied
   * (5) remove the last order and check isEmpty to verify that the queue HAS been emptied
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testPeekRemove() {
    Order.resetIDGenerator();
    
    // TODO implement this method, then go write the peek and dequeue methods in your
    // OrderPriorityQueue class so that they pass your tests
    try {    
      OrderPriorityQueue opq = new OrderPriorityQueue(10);
      opq.insert(new Order("steak", 500));
      opq.insert(new Order("cheesecake", 30));
      opq.insert(new Order("bread", 4));
      opq.insert(new Order("cake", 1));
      opq.insert(new Order("salad", 87));
      opq.insert(new Order("tea", 400));
  
      Order tmp = opq.removeBest();
      int prevprepTime = tmp.getPrepTime();
      
      for (int i=0; i<4; i++) {
        tmp = opq.removeBest();
        if (tmp.getPrepTime() > prevprepTime) {
          return false;
        }
        prevprepTime = tmp.getPrepTime();
      }
      if (!opq.peekBest().toString().equals("1004: cake (1)")) {
        System.out.println("peekBest fails!");
        return false;
      }
      if (opq.isEmpty()) {
        System.out.println("isEmpty fails!");
        return false;
      }
    }
    catch (Exception e) { // an unexpected exception was thrown
      e.printStackTrace();
      return false;
    }
    return true; // included to prevent compiler errors
  }
  
  /**
   * Checks the correctness of the removeBest and peekBest methods, as well as the constructor of 
   * the OrderPriorityQueue class for erroneous inputs and/or states
   * 
   * You should, at least:
   * (1) create a new OrderPriorityQueue with an invalid capacity argument, and verify that the 
   *     correct exception is thrown
   * (2) call peekBest() on an OrderPriorityQueue with an invalid state for peeking, and verify that
   *     the correct exception is thrown
   * (3) call removeBest() on an OrderPriorityQueue with an invalid state for removing, and verify
   *     that the correct exception is thrown
   * 
   * @return true if and only if ALL tests pass
   */
  public static boolean testErrors() {
    Order.resetIDGenerator();
    
    // TODO implement this method, then go modify the relevant methods in your
    // OrderPriorityQueue class so that they pass your tests
    
    try {
      try {
        OrderPriorityQueue opqInvalid = new OrderPriorityQueue(-5);
      }
      catch(IllegalArgumentException e) {
        // expected exception
        if(e.getMessage() == null || e.getMessage().length() == 0) { // exception with no error message
          System.out.println("exception not correctly catched");
          return false;
        }
      }
  
      OrderPriorityQueue opq = new OrderPriorityQueue(5);
      try {
        opq.peekBest();
      }
      catch(NoSuchElementException e) {
        // expected exception
        if(e.getMessage() == null || e.getMessage().length() == 0) { // exception with no error message
          System.out.println("exception not correctly catched");
          return false;
        }
      }
      
      try {
        opq.removeBest();
      }
      catch(NoSuchElementException e) {
        // expected exception
        if(e.getMessage() == null || e.getMessage().length() == 0) { // exception with no error message
          System.out.println("exception not correctly catched");
          return false;
        }
      }
      
    } 
    catch (Exception e) { // an unexpected exception was thrown
      e.printStackTrace();
      return false;
    }
    return true; // included to prevent compiler errors
  }
  
  /**
   * Calls the test methods individually and displays their output
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("isEmpty: "+testIsEmpty()+"\n");
    System.out.println("insert basic: "+testInsertBasic()+"\n");
    System.out.println("percolate UP: "+testPercolateUp()+"\n");
    System.out.println("insert advanced: "+testInsertAdvanced()+"\n");
    System.out.println("percolate DOWN: "+testPercolateDown()+"\n");
    System.out.println("peek/remove valid: "+testPeekRemove()+"\n");
    System.out.println("error: "+testErrors()+"\n");
  }

}
