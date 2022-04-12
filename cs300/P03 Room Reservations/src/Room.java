//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define the implementation details of Room class
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

import java.util.ArrayList;

/**
 * Implement the details how a Room class should work
 */
public class Room {
  
  private static ArrayList<String> names = new ArrayList<String>();
  // an ArrayList of Strings containing the currently-used Room names
  
  private String name; 
  // the name of Room instance
  private Person[] occupants; 
  // a perfect-size array of Persons currently in the room,
  // occupants.length == capacity 
  // social distance policy : Person only occupy even while odd is null
  private int currentOccupancy; 
  // the current number of Persons in the room 
  
  /**
   * Constructor
   * 
   * @param name - the name of Room
   * @param capacity - the maximum number of Persons 
   */
  public Room(String name, int capacity) throws IllegalArgumentException{
    // Constructor
    // capacity is the maximum number of Persons 
    // who can occupy the room [without] social distance enforced
    
    this.name=name;
    this.occupants = new Person[capacity];
    
    if (capacity<=0 || Room.names.contains(name)) {
      throw new IllegalArgumentException("the argument to create Room is not valid");
    }
    // If the provided capacity is 0 or less, 
    // or if the name is already in use by another Room object, 
    // throw an IllegalArgumentException with a descriptive error message
    
    Room.names.add(name);
    // If the Room is created successfully, add its name to the Room.names Array
  }

  /* Accessor */

  /**
   * Returns Room names in array
   * 
   * @return Room names in array
   */
  public static String[] getNames() {
    String[] namesInArray = new String[Room.names.size()];
    for (int i = 0; i<Room.names.size(); i++) {
      namesInArray[i] = names.get(i);
    }
    return namesInArray;
  }
  
  /**
   * Returns name of the Room instance
   * 
   * @return name of the Room instance
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * Returns how many persons are in the room
   * 
   * @return how many persons are in the room
   */
  public int getOccupancy() {
    return this.currentOccupancy;
  }
  
  /**
   * Returns the maximum size of occupants under social distance policy
   * 
   * @return the maximum size of occupants under social distance policy
   */
  public int getCOVIDCapacity() {
    // half of normal capacity
    int l = this.occupants.length;
    if (l%2==0) {return l/2;}
    else {return 1+l/2;} 
    // there will be on more even index when length being odd
  }
  
  /**
   * Returns the maximum size of occupants without social distance policy
   * 
   * @return the maximum size of occupants without social distance policy
   */
  public int getCapacity() {
    return this.occupants.length;
  }
  
  /**
   * Returns whether occupants contain specific Person
   * 
   * @param p - the Person to compare
   * @return whether occupants contain specific Person
   */
  public boolean contains(Person p) {
    for (int i=0; i<occupants.length; i++) {
      if (p.equals(occupants[i])) return true;
    } 
    return false;
  }
  
  /* Mutator */
  /**
   * Add a specific person into occupants
   * 
   * @param p - the Person to add
   * @return whether the Person is successfully added
   */
  public boolean checkIn(Person in) throws IllegalArgumentException{
    if (in==null || contains(in)) 
      throw new IllegalArgumentException
        ("the argument to check in Person is not valid");
    
    // when full dont checkin
    if (this.currentOccupancy==this.getCOVIDCapacity()) {
      return false;
    }
    
    for (int i=0; i<occupants.length; i+=2) {
      if (occupants[i]==null) {
        occupants[i]=in;
        this.currentOccupancy++;
        in.toggleWaiting();
        break;
      }
    }    
    
    return true;
  }
  
  /**
   * Delete a specific person in occupants
   * 
   * @param p - the Person to delete
   * @return whether the Person is successfully deleted
   */
  public boolean checkOut(Person in) throws IllegalArgumentException {
    if (in==null) throw new IllegalArgumentException("the argument to check out Person is not valid");
    
    if (!contains(in)) return false;
    
    this.currentOccupancy--;
    in.toggleWaiting();
    for (int i=0; i<occupants.length; i++) {
      if (in.equals(occupants[i])) {
        occupants[i]=null;
        break;
        }
    }
    return true;
  }
  
  /**
   * Returns a String representation of this Room and its occupants
   * 
   * @return a String representation of this Room and its occupants
   */
  public String toString(){
    String tmp = this.name + "\n" + "===" + "\n"; // string to increment 
    for (int i=0; i<occupants.length;i++) {
      if (occupants[i]!=null) {
        tmp+=occupants[i].getName()+"\n";
      }
      else {tmp+="-\n"; }
    }
    return tmp;
  }
}