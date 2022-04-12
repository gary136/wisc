// DO NOT SUBMIT THIS SOURCE FILE TO GRADESCOPE
public class Item implements Comparable<Item>{ 
  private final int ID; // unique identifier of this item
  private static int nextId = 1;
  private String name; // name of this item
  private double price; // price of this item

  public Item(String name, double price) {
   this.name = name;
   this.price = price;
   ID = nextId++;
  }
  
  public String getName() {  return this.name;  } // name accessor

  public double getPrice() {  return this.price;  } // phoneNumber accessor
  
  public int getID() { return this.ID; } // unique ID accessor

  public int compareTo(Item other) { return this.ID - other.ID;  }
  
}
