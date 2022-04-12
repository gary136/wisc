//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define LinkedBox
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
 * This class models a linked box node which can be used 
 * to create a singly linked list of boxes
 */

public class LinkedBox {
  private Box box;
  private LinkedBox next;
  
  /**
   * Constructor
   * a new LinkedBox with a specified box and null as next field
   * 
   * @param box - a specified box 
   */
  public LinkedBox(Box box) {
    this.box = box;
  }
  
  /**
   * Constructor
   * a new LinkedBox node with given box and next fields
   * 
   * @param box - a specified box 
   * @param next - a next box
   */
  public LinkedBox(Box box, LinkedBox next) {
    this.box = box;
    this.next = next;
  }
  
  /**
   * Returns the data field box 
   * 
   * @return the data field box
   */
  public Box getBox() {
    return this.box;
  }

  /**
   * returns the next linked box node
   * 
   * @return the next linked box node
   */
  public LinkedBox getNext() {
    return this.next;
  }


  /**
   * Returns a String representation of this Linked box
   *
   * @return a String representation of this Linked box
   */
  @Override
  public String toString() {
    if (this.next != null)
      return box.toString() + " -> ";
    else
      return box.toString() + " -> END";
  }

  /**
   * Sets the link to the next linked box node
   */
  public void setNext(LinkedBox next) {
    this.next = next;
  }
}
