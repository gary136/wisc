
public interface ListADT <T> {

  // general methods
  public void add(T thingToAdd);
  public boolean contains(T thingToCheck);
  public int size();
  
  // methods assuming an ordered list
  public void add(T thingToAdd, int index);
  public T get(int index);
  public int indexOf(T thingToCheck);
  public T remove (int index);
  
}
