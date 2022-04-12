
public interface StackADT <T> {
	// this stack holds references of any type T
	
	// adds new element to this stack
	public void push(T newElement); 
	
	// removes and returns element that was most recently 
	// added to this stack
	public T pop(); 
	
	// returns (without removing) element that was most 
	// recently added to this stack
	public T peek(); 
	
	// returns true when there are no elements in this stack, 
	// and false otherwise
	public boolean isEmpty(); 
}
