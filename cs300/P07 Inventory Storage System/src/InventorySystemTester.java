//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define InventorySystemTester
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
 * This class creates multiple tests 
 * to test the robustness of Inventory
 */
import java.util.NoSuchElementException;

public class InventorySystemTester { 
  /**
  * Helper method to display the size and the count of different
  * boxes stored in a list of boxes
  * 
  * @param list a reference to an InventoryList object
  * @throws NullPointerException if list is null
  */
  private static void displaySizeCounts(InventoryList list) throws NullPointerException{
    if (list==null) {
      throw new NullPointerException("the input is invalid");
    }
    System.out.println(" Size: " + list.size() +
    ", yellowCount: " + list.getYellowCount() +
    ", blueCount: " + list.getBlueCount() +
    ", brownCount: " + list.getBrownCount() 
    
//    +
//    ", head: " + 
//    list.getHead() +
//    ", tail: " + list.getTail() + "\n"
    );
  }
  
  // Make sure to call Box.restartNextInventoryNumber() at the beginning of every test method
  /**
  * Demo method showing how to use the implemented classes in P07 Inventory Storage System
  * @param args input arguments
  */
  public static void demo() {
  // Create a new empty InventoryList object
  Box.restartNextInventoryNumber();
  InventoryList list = new InventoryList();
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  // Add a blue box to an empty list
  list.addBlue​(new Box(Color.BLUE)); // adds BLUE 1
  System.out.println(list); // prints list’s content
  list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 2 at the head of the list
  System.out.println(list); // prints list’s content
  list.addBlue​(new Box(Color.BLUE)); // adds BLUE 3
  System.out.println(list); // prints list’s content
  list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 4
  System.out.println(list); // prints list’s content
  list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 5 at the head of the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  // Add more boxes to list and display its contents
  list.addBrown​(new Box(Color.BROWN)); // adds BROWN 6 at the end of the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.addBrown​(new Box(Color.BROWN)); // adds BROWN 7 at the end of the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  
  list.removeBrown(); // removes BROWN 7 from the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.addBlue​(new Box(Color.BLUE)); // adds BLUE 8
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.removeBrown(); // removes BROWN 6 from the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.removeYellow(); // removes YELLOW 5
  System.out.println(list); // prints list’s content
  list.removeBox​(3); // removes BLUE 3 from the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  try {
  list.removeBox​(25); // tries to remove box #25
  }
  catch(NoSuchElementException e) {
  System.out.println(e.getMessage());
  }
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  // remove all yellow boxes
  while(list.getYellowCount() != 0) {
  list.removeYellow();
  }
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.removeBox​(1); // removes BLUE 1 from the list -> empty list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.addBrown​(new Box(Color.BROWN)); // adds BROWN 9 to the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.removeBox​(8); // removes BLUE 8 from the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.removeBrown(); // removes BROWN 9 from the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 10 to the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  list.removeBox​(10); // removes YELLOW 10 from the list
  System.out.println(list); // prints list’s content
  displaySizeCounts(list); // displays list’s size and counts
  Box.restartNextInventoryNumber();
  }

  /**
   * test whether LinkedBopx works fine
   * 
   * @return true if functions of LinkedBox is good
   */
  public static boolean testLinkedBox() {
    Box.restartNextInventoryNumber();
    Box boxA = new Box(Color.BLUE);
    LinkedBox linkBoxA = new LinkedBox(boxA);
    if (!linkBoxA.getBox().equals(boxA)) return false;
    if (linkBoxA.getNext()!=null) return false;
    return true;
  }
  
  //checks for the correctness of the InventoryList.clear() method
  /**
   * checks for the correctness of the InventoryList.clear() method
   * 
   * @return true if InventoryList.clear() works correctly
   */
  public static boolean testClear() {
    Box.restartNextInventoryNumber();
    InventoryList list = new InventoryList();
    list.addBlue​(new Box(Color.BLUE));; // adds BLUE 1
    list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 2 
    list.addBrown​(new Box(Color.BROWN)); // adds BROWN 3 at the end of the list
    list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 4
    list.addBlue​(new Box(Color.BLUE)); // adds BLUE 5
    list.addBlue​(new Box(Color.BLUE)); // adds BLUE 6 
    list.addBlue​(new Box(Color.BLUE)); // adds BLUE 7 
    list.addBrown​(new Box(Color.BROWN)); // adds BROWN 8 at the end of the list
    list.addBlue​(new Box(Color.BLUE)); // adds BLUE 9
    // System.out.println(list);
    list.clear();
    // System.out.println(list);
    if (!list.isEmpty()||
        list.getBlueCount()!=0 ||
        list.getYellowCount()!=0 ||
        list.getBrownCount()!=0 ||
        list.size()!=0) return false;
    return true;
  }
  
  //checks for the correctness of the InventoryList.addYellow(),
  //InventoryList.addBlue(), and InventoryList.addBrown() methods
  /**
   * test whether addX works fine
   * 
   * @return true if addX works correctly
   */
  public static boolean testAddBoxes() {
    Box.restartNextInventoryNumber();
    InventoryList list = new InventoryList();
    list.addBrown​(new Box(Color.BROWN)); // BROWN 1 must be added at the head of the list.
    //  Then, cleared the list. So, list must be empty again. 
    list.clear();
    list.addBlue​(new Box(Color.BLUE)); // BLUE 2 must be added to the head of the list. 
    // Then again cleared the list
    list.clear();
    // called the following methods in order: 
     list.addYellow​(new Box(Color.YELLOW)); // must add YELLOW 3 to the head of the list.
     list.addBlue​(new Box(Color.BLUE)); // must add BLUE 4 at the tail of the list. 
     list.addYellow​((new Box(Color.YELLOW))); // must add YELLOW 5 to the head of the list.
     list.addBrown​((new Box(Color.BROWN))); // must add BROWN 6 to the tail of the list. 
     list.addBlue​((new Box(Color.BLUE))); // must add BLUE 7 at the position 2 of the list.
     list.addBrown​((new Box(Color.BROWN))); // must add BROWN 8 to the tail of the list.
    // Calling list.toString() at the end of this scenario must return: 
     // YELLOW 5 -> YELLOW 3 -> BLUE 7 -> BLUE 4 -> BROWN 6 -> BROWN 8 -> END
     // and that all the count fields and the size of the list are correct
    if (!list.toString().equals("YELLOW 5 -> YELLOW 3 -> BLUE 7 -> BLUE 4 -> BROWN 6 -> BROWN 8 -> END") ||
        list.getBlueCount()!=2 ||
        list.getYellowCount()!=2 ||
        list.getBrownCount()!=2 ||
        list.size()!=6
        )
      return false;
    return true;
  }

  //checks for the correctness of the InventoryList.removeBox()
  //InventoryList.removeYellow(), and InventoryList.removeBrown() methods
  /**
   * test whether removeX works fine
   * 
   * @return true if removeX works correctly
   */
  public static boolean testRemoveBoxes() {
    Box.restartNextInventoryNumber();
    // Create a new empty InventoryList object
    InventoryList list = new InventoryList();
    list.addBlue​(new Box(Color.BLUE)); // adds BLUE 1
    list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 2 at the head of the list
    list.addBlue​(new Box(Color.BLUE)); // adds BLUE 3
    list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 4
    list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 5 at the head of the list
    list.addBrown​(new Box(Color.BROWN)); // adds BROWN 6 at the end of the list
    list.addBrown​(new Box(Color.BROWN)); // adds BROWN 7 at the end of the list
  
    list.removeBrown(); // removes BROWN 7 from the list
    if (!list.toString().equals(
        "YELLOW 5 -> YELLOW 4 -> YELLOW 2 -> BLUE 3 -> BLUE 1 -> BROWN 6 -> END")) 
      return false;
    list.addBlue​(new Box(Color.BLUE)); // adds BLUE 8
    list.removeBrown(); // removes BROWN 6 from the list
    if (!list.toString().equals(
        "YELLOW 5 -> YELLOW 4 -> YELLOW 2 -> BLUE 8 -> BLUE 3 -> BLUE 1 -> END"))
      return false;
    list.removeYellow(); // removes YELLOW 5
    if (!list.toString().equals(
        "YELLOW 4 -> YELLOW 2 -> BLUE 8 -> BLUE 3 -> BLUE 1 -> END"))
      return false;
    list.removeBox​(3); // removes BLUE 3 from the list
    
    if (!list.toString().equals(
        "YELLOW 4 -> YELLOW 2 -> BLUE 8 -> BLUE 1 -> END"))
      return false;
    
    try {
    list.removeBox​(25); // tries to remove box #25
    }
    catch(NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    if (!list.toString().equals(
        "YELLOW 4 -> YELLOW 2 -> BLUE 8 -> BLUE 1 -> END"))
      return false;
    
    // remove all yellow boxes
    while(list.getYellowCount() != 0) list.removeYellow();
    if (!list.toString().equals(
        "BLUE 8 -> BLUE 1 -> END"))
      return false;
    list.removeBox​(1); // removes BLUE 1 from the list
    if (!list.toString().equals(
        "BLUE 8 -> END"))
      return false;
    list.addBrown​(new Box(Color.BROWN)); // adds BROWN 9 to the list
    list.removeBox​(8); // removes BLUE 8 from the list
    if (!list.toString().equals("BROWN 9 -> END")) 
      return false;
    list.removeBrown(); // removes BROWN 9 from the list -> empty list
    if (!list.toString().equals("")) 
      return false;
    list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 10 to the list
    list.removeBox​(10); // removes YELLOW 10 from the list
    if (!list.toString().equals("")) 
      return false;
    return true;
  }
  
  // checks for the correctness of 
  // the InventoryList.get() method
  /**
   * test whether InventoryList.get() works fine
   * 
   * @return true if InventoryList.get() works correctly
   */
  public static boolean testGetBoxes() {
    Box.restartNextInventoryNumber();
    InventoryList list = new InventoryList();
    list.addBlue​(new Box(Color.BLUE)); // adds BLUE 1
    list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 2 at the head of the list
    list.addBlue​(new Box(Color.BLUE)); // adds BLUE 3
    list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 4
    list.addYellow​(new Box(Color.YELLOW)); // adds YELLOW 5 at the head of the list
    list.addBrown​(new Box(Color.BROWN)); // adds BROWN 6 at the end of the list
    list.addBrown​(new Box(Color.BROWN)); // adds BROWN 7 at the end of the list
    list.removeYellow(); // removes YELLOW 5
    
    try {
      list.get(25); // tries to remove box #25
      }
    catch(NullPointerException e) {
      System.out.println(e.getMessage());
    }
    if (!list.get(0).toString().equals("YELLOW 4")) return false;
    // System.out.println(list);
    if (!list.get(5).toString().equals("BROWN 7")) return false;
    return true;
  }

  //a test suite method to run all your test methods
  /**
   * run all test methods
   * 
   * @return true if all methods works correctly
   */
  public static boolean runAllTests() {
    if (!testLinkedBox()) {
      System.out.println("testLinkedBox fails");
      return false;
    }
    if (!testClear()) {
      System.out.println("testClear fails");
      return false;
    }
    if (!testGetBoxes()) {
      System.out.println("testGetBoxes fails");
      return false;
    }
    if (!testRemoveBoxes()) {
      System.out.println("testRemoveBoxes fails");
      return false;
    }
    if (!testAddBoxes()) {
      System.out.println("testAddBoxes fails");
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println("All tests are passed: " + runAllTests());
//  demo();
  }

}
