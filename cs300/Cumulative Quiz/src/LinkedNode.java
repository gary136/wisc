// DO NOT SUBMIT THIS SOURCE FILE TO GRADESCOPE
/**
 * LinkedNode class represents a generic class of a node in a linked list
 * 
 * @param<T> represents the type of the data carried by this node
 */
public class LinkedNode<T> {
  // Fields
  private T data; // data field of any type T
  private LinkedNode<T> next; // reference to the next node in the list


  // Constructors

  /**
   * Creates an instanceof LinkedNode with a given data and no successor.
   * 
   * @param data data of this LinkedNode
   */
  public LinkedNode(T data) {
    this.data = data;
  }



  /**
   * Creates an instance of LinkedNode with given item and reference to next node
   * 
   * @param data value
   * @param next reference to the next node of the same type
   */
  public LinkedNode(T data, LinkedNode<T> next) {
    this.data = data;
    this.next = next;
  }



  /**
   * Accessor for item field
   * @return the item
   */
  public T getData() {
    return data;
  }



  /**
   * Setter of the item
   * 
   * @param data the item to set
   */
  public void setData(T data) {
    this.data = data;
  }



  /**
   * Accessor for next field
   * 
   * @return the next
   */
  public LinkedNode<T> getNext() {
    return next;
  }



  /**
   * Setter for next field
   * 
   * @param next the next to set
   */
  public void setNext(LinkedNode<T> next) {
    this.next = next;
  }
  
  /**
   * Returns a String representation of this Linked node.
   * 
   * @return a String representation of this Linked MegaBlock object
   */
  @Override
  public String toString() {
    if (this.next != null)
      return this.getData().toString() + " -> ";
    else
      return this.getData().toString() + " -> END";
  }
  

} // end generic class LinkedNode