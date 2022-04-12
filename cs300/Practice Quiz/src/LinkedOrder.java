/**
 * Wrapper for the Order objects, so that they can be queued in a LinkedList format
 */
public class LinkedOrder {

  private Order1 order;      // the Order associated with this linked node
  private LinkedOrder next; // the LinkedOrder behind this one in the queue
  
  /**
   * Creates a new LinkedOrder with no successor
   * @param o the Order associated with this LinkedOrder
   */
  public LinkedOrder(Order1 o) {
    this.order = o;
  }
  
  /**
   * Returns the Order associated with this LinkedOrder
   * 
   * @return the Order associated with this LinkedOrder
   */
  public Order1 getOrder() {
    return this.order;
  }
  
  /**
   * Returns the LinkedOrder behind this one in the queue
   * 
   * @return the LinkedOrder behind this one in the queue
   */
  public LinkedOrder getNext() {
    return this.next;
  }
  
  /**
   * Updates the LinkedOrder behind this one in the queue to be the one provided
   * 
   * @param newNext the new LinkedOrder to follow this one in the queue
   */
  public void setNext(LinkedOrder newNext) {
    this.next = newNext;
  }
  
}
