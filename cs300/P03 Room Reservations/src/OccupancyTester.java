//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: test the robustness of Person and Room
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
 * Implement the details how a tester for Person and Room should work
 */
public class OccupancyTester {
  // Create two (or more) instances of the ​Person ​object 
  // by calling the constructor with different names. 
  private static Person p1 = new Person("Mary");
  private static Person p2 = new Person("John");
  private static Person p3 = new Person("Pinlake");
  private static Person p4 = new Person("Alex");
  private static Person p5 = new Person("Mary");

  private static String s1 = new String("Mary");
  
  //Create at least two different ​Rooms​
  private static Room r1 = new Room("CS124", 6);
  private static Room r2 = new Room("CS700", 50);
  
  /**
   * Test the constructor, accessor, mutator, and equals method of ​Person ​class
   * 
   * @return whether Person is all right
   */
  public static boolean testPerson() {
    
    // test the constructor & accessor
    // Use the accessor methods to verify that each object returns different values 
    // (and that they’re the ones you expect for that particular instance).
    if (p1.getName()!="Mary" || p2.getName()!="John"
        || p1.getName()==p2.getName()
        || !p1.isWaiting() || !p2.isWaiting()) return false; 
    
    // test the mutator
    // Verify that the toggleWaiting() method flips the isWaiting value 
    // from false to true and back
    p1.toggleWaiting();
    if (p1.isWaiting()) return false;
    p1.toggleWaiting();
    if (!p1.isWaiting()) return false;
    
    // test the equals 
    // Verify that two ​Person ​instances with different names are NOT considered equal,  
    // and that two Person ​instances with the same name ARE considered equal. 
    // A ​Person ​and any other object (say, a String) should NOT be considered equal
    if (p1.equals(p2) || !p1.equals(p5) || p1.equals(s1)) return false;
    
    return true;
  }
  
  /**
   * Test the constructor of Room ​class
   * 
   * @return whether constructor of Room ​class is all right
   */
  public static boolean testRoomConstructor() {
    // Try creating ​Rooms ​with the same name
    try {
      Room r3 = new Room("CS124", 10);
    }
    
    // Make sure you can’t.
    catch (IllegalArgumentException excpt) {
      System.out.println(excpt.getMessage());
    }
    
    // Make sure the names array is in the correct order
    String[] roomNames = Room.getNames();
    if (roomNames[0]!="CS124" || roomNames[1]!="CS700") return false;
    return true;
  }
  
  /**
   * Test the accessor methods of Room ​class
   * 
   * @return whether the accessor methods of Room ​class is all right
   */
  public static boolean testRoomAccessor() {
    // test the static method
    String[] roomNames = Room.getNames();
    if (roomNames[0]!="CS124" || roomNames[1]!="CS700") return false;
    
    // test the instance method
    if (r1.getName()!="CS124" 
        || r1.getCapacity()!=6 
        || r1.getOccupancy()!=0
        || r1.getCOVIDCapacity()!=3
        || r1.contains(p1)
        ) return false;
    return true;
  }
  
  /**
   * Test the check-in functionality and its effects on both the ​Room 
   * ​and the ​Person ​being checked in
   * 
   * @return whether the CheckIn methods of Room ​class is all right
   */
  public static boolean testRoomCheckIn() {
    // Try checking in null
    try {r1.checkIn(null);}
    // Make sure you can’t.
    catch (IllegalArgumentException excpt) {System.out.println(excpt.getMessage());}

    // check whether the person has been added
    // check whether currentOccupancy has been incremented
    // check whether the added person isn't waiting
    
    if (!r1.checkIn(p1)) return false;
    if (!testRoomToString() || r1.getOccupancy()!=1 || p1.isWaiting()) return false;
    
    if (!r1.checkIn(p2)) return false;
    if (!testRoomToString() || r1.getOccupancy()!=2 || p2.isWaiting()) return false;
    
    if (!r1.checkIn(p3)) return false;
    if (!testRoomToString() || r1.getOccupancy()!=3 || p3.isWaiting()) return false;

    // check whether the person is not checked in due to capacity
    if (r1.checkIn(p4)) return false;
    // check whether currentOccupancy has not been added
    if (!testRoomToString() || r1.getOccupancy()!=3) return false;
    
    // Try checking in person with same name
    try {
      r1.checkIn(p5);
    }
    // Make sure you can’t.
    catch (IllegalArgumentException excpt) {
      System.out.println(excpt.getMessage());
    }
    return true;
  }
  
  /**
   * Test the check-out functionality and its effects on both the ​Room 
   * ​and the ​Person ​being checked out
   * 
   * @return whether the CheckOut methods of Room ​class is all right
   */
  public static boolean testRoomCheckOut() {
    // Try checking out null
    try {
      r1.checkOut(null);
    }
    // Make sure you can’t.
    catch (IllegalArgumentException excpt) {
      System.out.println(excpt.getMessage());
    }
    
    // check whether the person has been deleted
    // check whether currentOccupancy has been decremented
    // check whether the deleted person is waiting
    
    if (!r1.checkOut(p1)) return false;
    if (!testRoomToString() || r1.getOccupancy()!=2 || !p1.isWaiting()) return false;
    
    if (!r1.checkOut(p2)) return false;
    if (!testRoomToString() || r1.getOccupancy()!=1 || !p2.isWaiting()) return false;
    
    if (!r1.checkOut(p3)) return false;
    if (!testRoomToString() || r1.getOccupancy()!=0 || !p3.isWaiting()) return false;
    
    
    
    // Try checking out person not in the occupants
    try {
      r1.checkOut(p4);
    }
    // Make sure you can’t.
    catch (IllegalArgumentException excpt) {
      System.out.println(excpt.getMessage());
    }
    return true;
  }
  
  /**
   * Verify the result of ​Room’​s toString method
   * 
   * @return whether the toString methods of Room ​class is all right
   */
  public static boolean testRoomToString() {
    // the contains here is String.contains() to check 
    // whether the whole message contains specific string 
    if (r1.contains(p1)) {
      if (!r1.toString().contains(p1.getName())) return false;
    }
    else {
      if (r1.toString().contains(p1.getName())) return false;
    }
    
    if (r1.contains(p2)) {
      if (!r1.toString().contains(p2.getName())) return false;
    }
    else {
      if (r1.toString().contains(p2.getName())) return false;
    }
    
    if (r1.contains(p3)) {
      if (!r1.toString().contains(p3.getName())) return false;
    }
    else {
      if (r1.toString().contains(p3.getName())) return false;
    }
    
    return true;
  }
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println("testPerson: " + testPerson());
    System.out.println("testRoomConstructor: " + testRoomConstructor());
    System.out.println("testRoomAccessor: " + testRoomAccessor());
    System.out.println("testRoomcheckIn: " + testRoomCheckIn());
    System.out.println("testRoomcheckOut: " + testRoomCheckOut());
    System.out.println("testRoomToString: " + testRoomToString());
  }
}