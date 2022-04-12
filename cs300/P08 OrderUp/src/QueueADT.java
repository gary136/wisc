/**
 * The methods required for a queue.
 *
 * @param <T> The object type contained within the queue
 */
public interface QueueADT <T> {
  
  /**
   * Add a new element to the back of the queue.
   * 
   * @param newElement the element to be added.
   */
  public void enqueue(T newElement);
  
  /**
   * Remove the next element from the front of the queue. Throws a NoSuchElementException if the
   * queue is empty.
   * 
   * @return the next element from the front of the queue.
   */
  public T dequeue();
  
  /**
   * Returns the next element from the front of the queue without removing it. Throws a
   * NoSuchElementException if the queue is empty.
   * 
   * @return the next element from the front of the queue.
   */
  public T peek();
  
  /**
   * Returns true if and only if the queue contains no elements.
   * 
   * @return true if the queue is empty, false otherwise
   */
  public boolean isEmpty();
}
