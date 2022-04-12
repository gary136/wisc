//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define OrderQueue
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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * OrderQueue implements the QueueADT<Order> AND Iterable<Order> interfaces
 */

public class OrderQueue implements Iterable<Order>, QueueADT<Order>{
  private LinkedOrder front = null; // a LinkedOrder at the front of the queue
  private int size = 0; // an integer tracking the number of Orders currently in the queue
  private LinkedOrder back = null; // a LinkedOrder at the back of the queue
  
//  // default 
//  public OrderQueue() {
//    
//  }
  
  /**
   * Creates and returns a new OrderIterator beginning with the current value of front
   * 
   * @return a new OrderIterator beginning with the current value of front
   */
  @Override
  public Iterator<Order> iterator() {
    // a new OrderIterator
    // public OrderIterator(LinkedOrder start) {
    //   this.current = start;
    // }
    // note this.front becomes OrderIterator.current
    OrderIterator newX = new OrderIterator(this.front);
    return newX;
  }
  

  
  /**
   * Adds a new LinkedOrder containing newElement to the back of the queue, 
   * updating the size variable and front/back references appropriately
   * 
   * @param newElement 
   */
  @Override
  public void enqueue(Order newElement) {
    LinkedOrder newX = new LinkedOrder(newElement);
    
    if (front == null) { // if empty list
      front = newX;
      back = newX;
      this.size++;
    }
    
    else {
      back.setNext(newX);
      back = newX;
      this.size++;
    }
  }
  
  /**
   * Removes the next LinkedOrder from the front of the queue 
   * and returns its Order, updating the size variable and front/back references appropriately 
   * Throws a NoSuchElementException if the queue is empty
   * 
   * @return the Order that is removed
   */
  @Override
  public Order dequeue() throws NoSuchElementException {
    if (this.size == 0) throw new NoSuchElementException("the queue is empty");
    
    LinkedOrder curr = front;
    if (this.size == 1) { // if front==back      
      front = null;
      back = null;
      this.size--;
    }     
    
    else {
      LinkedOrder succ = front.getNext(); // this new front
      front = succ;
      this.size--;
    }
    
    return curr.getOrder();
  }
  
  /**
   * Returns the Order from the LinkedOrder at the front of the queue without 
   * removing theLinkedOrder from the queue 
   * Throws a NoSuchElementException if the queue is empty
   * 
   * @return the Order from the LinkedOrder at the front
   */
  @Override
  public Order peek() throws NoSuchElementException {
    if (this.size == 0) throw new NoSuchElementException("the queue is empty");
    
    return front.getOrder();
  }
  
  /**
   * Returns true if and only if the queue is empty
   * 
   * @return true if and only if the queue is empty
   */
  @Override
  public boolean isEmpty() {
    if (this.size == 0) return true;
    return false;
  }

  /**
   * Creates and returns a String representation of this OrderQueue
   * using an enhanced-for loop. For example, a queue with three Orders
   * might look like this:
   * 1001: fries (2) -> 1002: shake (1) -> 1003: burger(3) -> END
   * 
   ** @returnA String representation of the queue*/
  @Override
  public String toString() {
    if(this.size == 0) return "";
  String qString = "";
  for (Order o :this) {
    qString += o.toString();
    qString += " -> ";
  }
    qString += "END";
    return qString;
  }

}
