//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define OrderIterator
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
 * OrderIterator implements the Iterator<Order> interface
 * the iterator of LinkedOrder
 * 
 */
public class OrderIterator implements Iterator<Order>{
  private LinkedOrder current;
  
  /**
   * Constructor, initializes current to the provided starting LinkedOrder
   * 
   * @param start - provided starting LinkedOrder
   */
  public OrderIterator(LinkedOrder start) {
    this.current = start;
  }
  
  /**
   * Returns true if and only if the iteration has more orders
   * 
   * @return true if and only if the iteration has more orders
   */
  public boolean hasNext() {
    // next() actually returns curr so here checks current
    return this.current!=null;
  }
  
  
  
  /**
   * Throws a NoSuchElementException with a descriptive error message if the iteration 
   * does not have more orders to return
   * Otherwise returns the next Order and updates the current field appropriately
   * 
   * @return the next Order
   */
  public Order next() throws NoSuchElementException {
    if (!this.hasNext()) throw new NoSuchElementException("no more element");
    // when foreach is used, the object returned is next()
    // to ensure the first element can be accessed, next() actually returns curr
    Order data = this.current.getOrder();
    this.current = this.current.getNext();
    return data;
  }
}
