
public interface QueueADT <T> { 
	// this queue holds references of any type T
	
	// adds new element to this queue
	public void enqueue(T newElement);
  
	// removes and returns element that has been in this 
	// queue the longest
	public T dequeue();
	
	// returns (without removing) element that has been 
	// in this queue the longest
	public T peek();
	
	// returns true when there are no elements in this queue
	// and false otherwise
	public boolean isEmpty();
}
