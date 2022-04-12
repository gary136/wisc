//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define InventoryList
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
 * This class models a singly linked list of boxes which can be 
 * used to store and manage inventory
 */
import java.util.NoSuchElementException;

public class InventoryList {
  private LinkedBox head;
  private LinkedBox tail;
  private int size = 0;
  private int yellowCount = 0;
  private int blueCount = 0;
  private int brownCount = 0;

  /**
   * Adds a new blue box at the top of blue boxes if 
   * the list contains any blue box
   * 
   * @param blueBox - a specified blue box 
   */
  public void addBlue​(Box blueBox) throws IllegalArgumentException{
  // BLUE boxes can be added ABOVE all blue boxes already added to the list, 
  // but AFTER all the already added YELLOW boxes, and BEFORE all the 
  // already added BROWN boxes. 
  // -> BLUE MUST be added at position yellowCount of the inventory list
    
  // The color of the box is not blue/the box is null -> throw an exception
    
  if (blueBox.getColor()!=Color.BLUE || blueBox==null) {
    throw new IllegalArgumentException("the argument is invalid");
  }
    
    // 1. Adding a box to an empty list 
    if (yellowCount < 1 && blueCount < 1 && brownCount < 1) {
        LinkedBox newX = new LinkedBox(blueBox); 
        // a new blue box whose next points to null
        head = newX; // make new node the head
        tail = newX; // make new node the tail
        blueCount++;
        size++;
    }
    else {
      // if no YELLOW boxes 
      
      // 2. Adding a box to the head of the list
      if (yellowCount < 1) {
        // BLUE boxes can be added at the head of the list if 
        // no YELLOW boxes are present in the list
        
        // here blue >= 1 or brown >= 1
        // whether head is blue or brown does not matter 
        // the new blue is added before original head anyway
        LinkedBox curr = head;
        LinkedBox newX = new LinkedBox(blueBox, curr); 
        // a new blue box whose next points to original head
        head = newX; // make new node the head
        blueCount++;
        size++;
      }
      
      // if existing YELLOW boxes
      else {
        // 3. Adding a box at the tail of the list
        
        // this only happens if blue < 1 and brown < 1
        if (blueCount < 1 && brownCount < 1) { // tail is yellow
          LinkedBox newX = new LinkedBox(blueBox); 
          tail.setNext(newX); // make tail's next points to the new node
          tail = newX; // update the tail
          blueCount++;
          size++;
        }
  
        // 4. Adding a box at the middle of the list
        
        // this happens if blue >= 1 or brown >= 1
        else {
          LinkedBox curr = head;
          // traverse to last yellow 
          // suppose yellowCount = 4, lstY = 3 (y -> 0 1 2 3)
          int lstY = 0;
          while (lstY < yellowCount-1) {
            curr = curr.getNext();
            lstY++;
          }
          LinkedBox prev = curr; // last yellow
          curr = curr.getNext(); // first blue or brown
          LinkedBox newX = new LinkedBox(blueBox, curr); 
          // a new blue box whose next points to original first blue or brown
          prev.setNext(newX); // make last yellow's next points to the new node
          blueCount++;
          size++;
        }
      }
    }
  }

  /**
   * Adds a brown box at the end of this inventory list
   * 
   * @param brownBox - a specified brown box 
   */
  public void addBrown​(Box brownBox) throws IllegalArgumentException{
  // BROWN boxes can be added only at the end (tail) of the list
  
 // The color of the box is not brown/the box is null -> throw an exception
    
  if (brownBox.getColor()!=Color.BROWN || brownBox==null) {
    throw new IllegalArgumentException("the argument is invalid");
  }
    
      // 1. Adding a box to an empty list 
      if (yellowCount < 1 && blueCount < 1 && brownCount < 1) {
          LinkedBox newX = new LinkedBox(brownBox); 
          // a new brown box whose next points to null
          head = newX; // make new node the head
          tail = newX; // make new node the tail
          brownCount++;
          size++;
      }

      else {
        // 2. Adding a box to the head of the list
        // -> impossible in non empty list
      
        // 3. Adding a box at the tail of the list
        LinkedBox newX = new LinkedBox(brownBox); 
        tail.setNext(newX); // make tail's next points to the new node
        tail = newX; // make new node the tail
        brownCount++;
        size++;

        // 4. Adding a box at the middle of the list
        // -> impossible in non empty list
      }
  }

  /**
   * Adds a new yellow box at the head of this list
   * 
   * @param yellowBox - a specified yellow box 
   */
  public void addYellow​(Box yellowBox) throws IllegalArgumentException{
  // YELLOW boxes can be added only at the head of the list
  // The color of the box is not yellow/the box is null -> throw an exception
    
  if (yellowBox.getColor()!=Color.YELLOW || yellowBox==null) {
    throw new IllegalArgumentException("the argument is invalid");
  }
    
    // 1. Adding a box to an empty list 
    if (yellowCount < 1 && blueCount < 1 && brownCount < 1) {
        LinkedBox newX = new LinkedBox(yellowBox); 
        // a new brown box whose next points to null
        head = newX; // make new node the head
        tail = newX; // make new node the tail
        yellowCount++;
        size++;
    }

    else {
    // 2. Adding a box to the head of the list
      // here blue >= 1 or brown >= 1
      LinkedBox curr = head;
      LinkedBox newX = new LinkedBox(yellowBox, curr);  
      // a new yellow box whose next points to original head
      head = newX; // make new node the head
      yellowCount++;
      size++;
    
      // 3. Adding a box at the tail of the list
      // -> impossible in non empty list

      // 4. Adding a box at the middle of the list
      // -> impossible in non empty list
    }
  }
  
  /**
   * Removes all of the elements from this list
   */
  public void clear() {
    // if (head==null) -> nothing
    
    if (head!=null) {
      while (head!=null) {
        LinkedBox curr = head;
        decNumr(curr);
        head = head.getNext();
        curr = null;
        // traverse all down, curr -> null
      }
      tail = null;
    }
  }
  
  /**
   * Returns the element stored at position index of this 
   * list without removing it 
   * 
   * @param index - the position of box 
   * @return the element stored at position index
   */
  public Box get(int index) throws NullPointerException{
    // suppose 4 boxes, index = 3 -> 2 1 0
    LinkedBox curr = head;
    while (index > 0) {
      curr = curr.getNext();
      if (curr==null) {
        throw new NullPointerException("cannot find specific element");
      }
      index--;
    }
    return curr.getBox();
  }

  /**
   * Returns the number of blue boxes stored in this list 
   * 
   * @return the number of blue boxes stored in this list
   */
  public int getBlueCount() {
    return blueCount;
  }

  /**
   * Returns the number of brown boxes stored in this list
   * 
   * @return the number of brown boxes stored in this list
   */
  public int getBrownCount() {
    return brownCount;
  }

  /**
   * Returns the number of yellow boxes stored in this list
   * 
   * @return the number of yellow boxes stored in this list
   */
  public int getYellowCount() {
    return yellowCount;
  }
  
  /**
   * Checks whether this LinkedBoxList is empty
   * 
   * @return true if LinkedBoxList is empty
   */
  public boolean isEmpty() {
    if (blueCount+brownCount+yellowCount == 0) return true;
    return false;
  }

  /**
   * a helper to address number when removal occurs
   * 
   * @param curr - the node to be removed 
   */
  private void decNumr(LinkedBox curr) {
    // node curr -> the node to be removed
    if (curr.getBox().getColor()==Color.BLUE) blueCount--;
    else if (curr.getBox().getColor()==Color.BROWN) brownCount--; 
    else yellowCount--; 
    size--;
  }
  
  /**
   * Removes and returns a box given its inventory number from this list
   * 
   * @param inventoryNumber - the specified position to remove
   * @return a box given its inventory number
   */
  public Box removeBox​(int inventoryNumber) throws NoSuchElementException{
    // List empty -> throw an exception
    if (this.isEmpty()) {
      throw new NoSuchElementException("cannot find specific element");
    }
    
    LinkedBox curr = head;
    LinkedBox prev = null;
    // traverse till inventoryNumber matches
    while (curr!=null) {
      if (curr.getBox().getInventoryNumber()==inventoryNumber) break;
      prev = curr;
      curr = curr.getNext();
      // if still not match when end, curr -> null, prev -> last element
    }
    // 4. The box is not in the list. 
    // This includes the case of an empty list.
    if (curr==null) {
      throw new NoSuchElementException("cannot find specific element");
    }
    
    // 1. The box is the first one in the list.
    if (curr.equals(head)) {
      LinkedBox newHead = head.getNext();
      head = newHead;
      decNumr(curr);
      
      if (size==1) tail = head;
      if (size==0) tail = null;
    }
    // 2. The box is in the middle of the list 
    // (there are boxes both before AND after it)
    if (prev!=null && curr.getNext()!=null) {
      prev.setNext(curr.getNext());
      decNumr(curr);
    }
    // 3. The box is at the end of the list
    if (prev!=null && curr.getNext()==null) {
      prev.setNext(null);
      decNumr(curr);
      tail = prev;
    }
    
    return curr.getBox(); // case 1 2 3
  }

  /**
   * Removes and returns the element at the tail of this list if 
   * it has a brown color
   * 
   * @return the element at the tail if that is brown
   */
  public Box removeBrown() throws NoSuchElementException{
    // List empty -> throw an exception
    if (this.isEmpty()) {
      throw new NoSuchElementException("cannot find specific element");
    }
    
    // 1. Trying to remove from an empty list, or specific box to be removed not found.
    if (head==null) {
      return null;
    }
    LinkedBox curr = head;
    LinkedBox prev = null;
    
    // 2. Removing a box from a list which contains only one box
    if (blueCount+brownCount+yellowCount == 1) {
      if (curr.getBox().getColor()==Color.BROWN) {
        head = null;
        tail = null;
        brownCount--; 
        size--;
      }
    }
    
    // 3. Removing a box from the tail of a non-empty list 
       // will need a reference to the previous node
    else {
      while (curr.getNext()!=null) {
        prev = curr;
        curr = curr.getNext();
        // traverse all down, curr -> last element, prev -> last 2nd element
      }
      if (curr.getBox().getColor()==Color.BROWN) {
        prev.setNext(null);
        tail = prev;
        brownCount--; 
        size--;
      }
    }
    return curr.getBox();
  }

  /**
   * Removes and returns the box at the head of this list if 
   * its color is yellow
   * 
   * @return the box at the head if that is yellow
   */
  public Box removeYellow() throws NoSuchElementException{
    // List empty -> throw an exception
    if (this.isEmpty()) {
      throw new NoSuchElementException("cannot find specific element");
    }
    
    // 1. Trying to remove from an empty list, or specific box to be removed not found.
    if (head==null) {
      return null;
    }
    
    // 2. Removing a box from a list which contains only one box
    LinkedBox curr = head;
    if (blueCount+brownCount+yellowCount == 1) {
      if (curr.getBox().getColor()==Color.YELLOW) {
        head = null;
        yellowCount--; 
        size--;
      }
    }
    
    // 3. Removing the head
    else {
      if (curr.getBox().getColor()==Color.YELLOW) {
        head = head.getNext();
        yellowCount--; 
        size--;
      }
    }
    return curr.getBox();
  }

  /**
   * Returns the size of this list
   * 
   * @return the size of this list
   */
  public int size() {
    return size;
  }

  /**
   * Returns a String representation of the contents of this list
   *
   * @return a String representation of the contents of this list
   */
  @Override
  public String toString() {
    
    // traverse the contents of the list and return the following String
    if (this.isEmpty() == true)
      return ""; // An empty String ""; if the list is empty
    else {
      // A string containing an String representation of each box 
      // separated by " -> "s, and ending with " -> END";
      LinkedBox curr = head;
      String res = "";
      while (curr!=null) {
        res += curr.getBox().toString() + " -> ";
        curr = curr.getNext();
      }
      return res + "END";
    }
  }
  
////  temp get head and tail
  
//  public int getHead() {
//    if (head==null) return -1;
//    return head.getBox().getInventoryNumber();
//  }
  
//  public int getTail() {
//    if (tail==null) return -1;
//    return tail.getBox().getInventoryNumber();
//  }
  
}
