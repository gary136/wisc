// --== CS400 File Header Information ==--
// Name: Lee Hung Ting
// Email: hlee864@@wisc.edu
// Notes to Grader: None

import java.util.NoSuchElementException;
import java.util.Arrays;
import java.util.LinkedList;

public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{
  // use a private array instance field within your HashTableMap class to store key-value pairs.
  private int capacity;
  private LinkedList<Pair>[] hashtable;
  
  // constructor
  public HashTableMap(int capacity) {
    this.capacity = capacity;
    hashtable = new LinkedList[capacity];
    for (int i=0; i<hashtable.length; i++) {      
      hashtable[i] = new LinkedList<Pair>();
    }
  }
  
  // constructor
  public HashTableMap() {
    this.capacity = 10;
    hashtable = new LinkedList[capacity];
    for (int i=0; i<hashtable.length; i++) {      
      hashtable[i] = new LinkedList<Pair>();
    }
  }
  
  /**
  * grow by doubling capacity and rehashing, 
  * whenever its load capacity becomes 
  * greater than or equal to 80%.
  */
  private void grow() {
    if (this.size()>=0.8*this.capacity) {
//      System.out.println("\nsize = " + this.size() + " >= capacity*0.8 = " + this.capacity*0.8 + ", woulg trigger grow");
      int oldcapacity = this.capacity;
      this.capacity*=2;

//      System.out.println("\nbefore rehashing");
//      for (LinkedList<Pair> l :hashtable) {  
//        System.out.print(l);
//        for (Pair p : l) {
//          System.out.print("("+p.key() + ", " + p.value()+"), ");
//        }
//        System.out.print("\n");
//      }

      // copy hashtable data
      LinkedList<Pair>[] hashtablecopy = new LinkedList[oldcapacity];
      for (int i=0; i<hashtablecopy.length; i++) {      
        hashtablecopy[i] = hashtable[i];        
      }
      
      // empty and enlarge the hashtable
      this.hashtable = new LinkedList[this.capacity];
      for (int i=0; i<hashtable.length; i++) {      
        hashtable[i] = new LinkedList<Pair>();
      }       
      
      // move data from hashtablecopy to hashtable
      for (int i=0; i<hashtablecopy.length; i++) {  
        LinkedList<Pair> l = hashtablecopy[i];
        for (Pair p : l) {
          this.put((KeyType) p.key(), (ValueType) p.value());
        }
      }
      
//      System.out.println("\nafter rehashing");
//      for (LinkedList<Pair> l :hashtable) {  
//        System.out.print(l);
//        for (Pair p : l) {
//          System.out.print("("+p.key() + ", " + p.value()+"), ");
//        }
//        System.out.print("\n");
//      }
    }
  }  
      
  /**
   * store new values in your hash table at the index corresponding to the 
   * ( absolute value of your key's hashCode() ) modulus the HashTableMap's current 
   * capacity. When the put method is passed a key that is null or is already in your 
   * hash table (use .equals() to check for this equality), that call should return 
   * false without making any changes to the hash table. The put method should 
   * only return true after successfully storing a new key-value pair.
   */
  @Override
  public boolean put(KeyType key, ValueType value) {
    if (key==null) return false;    
//    System.out.print("k = " + key + " | ");        
    int index = Math.abs(key.hashCode()) % this.capacity;
//    System.out.println("hash, index = " + key.hashCode() + ", " + index);
    LinkedList<Pair> l = hashtable[index];
    for (Pair p : l) {
      if (p.key().equals(key)) return false;
    }   
        
    Pair p = new Pair(key, value);
    l.add(p);
    if (this.size()>=0.8*this.capacity) {
      this.grow();
    }
    return true;
  }

  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    // TODO Auto-generated method stub
    if (key==null) return null; 
//    System.out.println("k = " + key);  
    int index = Math.abs(key.hashCode()) % this.capacity;
    LinkedList<Pair> l = hashtable[index];
    for (Pair p : l) {
      if (p.key().equals(key)) return (ValueType) p.value();
    }
    throw new NoSuchElementException("key not in table");
  }
  
  /**
  include a size method that returns the number of key-value pairs stored in this collection
  */
  @Override
  public int size() {
    // TODO Auto-generated method stub
    int size = 0;
    for (LinkedList<Pair> l :hashtable) {   
      size+=l.size();
    }
    return size;
  }

  @Override
  public boolean containsKey(KeyType key) {
    // TODO Auto-generated method stub
    if (key==null) return false;
    
    int index = key.hashCode() % this.capacity;
    LinkedList<Pair> l = hashtable[index];
    for (Pair p : l) {
      if (p.key().equals(key)) return true;
    }
    return false;
  }  
  
  /**
  include a remove method that returns a reference to the value associated with 
  the key that is being removed.  When the key being removed does not exist, this 
  method should instead return null.
   */
  @Override
  public ValueType remove(KeyType key) {
    // TODO Auto-generated method stub
    if (key==null) return null;
    
    int index = key.hashCode() % this.capacity;
    LinkedList<Pair> l = hashtable[index];
    for (Pair p : l) {
      if (p.key().equals(key)) {
        ValueType v = (ValueType) p.value();
        l.remove(p);
        return v;
      }
    }
    
    return null;
  }

  
  /**
  include a clear method that removes all key-value pairs from this collection
  */
  @Override
  public void clear() {
    // TODO Auto-generated method stub
    for (LinkedList<Pair> l :hashtable) {  
      l.clear();
    }
  }   
  
    public int getCapacity() {
    return this.capacity;
  }
  
//  public boolean toString() {
//    String res = "Date\tPrice";
//    if (key==null) return false;
//    
//    int index = key.hashCode() % this.capacity;
//    LinkedList<Pair> l = hashtable[index];
//    for (Pair p : l) {
//      if (p.key().equals(key)) return true;
//    }
//    return false;
//  }  
  
//  public static void main(String[] args) {
////    Pair x = new Pair(1.0, 2.0);
////    System.out.println("x = "+"("+x.key()+", "+x.value()+")");
////   
//    
//  /**
//  (-2.0): Tests get(3) on a HashTableMap with keys: 1, 2, 3
//  Encountered trouble running HashTableMap.put with arguments: [class java.lang.Integer, class java.lang.String]. 
//  Encountered NullPointerException in file HashTableMap.java on line 75.
//  Note: submission/HashTableMap.java uses unchecked or unsafe operations.
//  Note: Recompile with -Xlint:unchecked for details.
//  
//  (-1.0): Tests get(7) on a HashTableMap with keys: 6, 8, 9
//  Encountered trouble running HashTableMap.put with arguments: [class java.lang.Integer, class java.lang.String]. 
//  Encountered NullPointerException in file HashTableMap.java on line 75.
//  Note: submission/HashTableMap.java uses unchecked or unsafe operations.
//Note: Recompile with -Xlint:unchecked for details.
//  */
//    
//    // test
//    HashTableMap a = new HashTableMap(6);
//    System.out.println("size = " + a.size());
//    a.put(3, "a");
////    a.put(13, "b");
////    a.put(781216748, "some number experiment");
//    
//    /**
//    (-1.0): Tests the specific load factor that triggers growth in private internal array.
//    (-2.0): Tests size of a HashTableMap(6)'s private internal array before and after growing.
//    */
//    a.put(5, "a");
////    a.put(6, "a");
//    a.put(7, "a");
//    a.put(8, "a");
////    a.put(1, "test");
////    System.out.println("a contains 1: " + a.containsKey(1));
////    System.out.println("remove value of key 1: " + a.remove(1));
////    System.out.println("a contains 1: " + a.containsKey(1));
//    a.put(4, "a");
//    System.out.println("size = " + a.size());
//    a.put(12, "a");
//    System.out.println("size = " + a.size());
////    a.grow();
//  }      
}