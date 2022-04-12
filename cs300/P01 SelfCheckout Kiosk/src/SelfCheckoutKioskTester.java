//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: (descriptive title of the program making use of this file)
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

// JavaDoc class Header comes here
/**
 * Test whether the methods in SelfCheckoutKiosk work as expected
 */
public class SelfCheckoutKioskTester {

  /**
   * Checks whether SelfCheckoutKisok.getItemName() and SelfCheckoutKisok.getItemPrice() method work
   * as expected.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testItemNameAndPriceGetterMethods() {
    // consider all identifiers values as input arguments
    // GROCERY_ITEMS array is a perfect size array. So, its elements are stored
    // in the range of indexes from 0 .. GROCERY_ITEMS.length -1
    for (int i = 0; i < SelfCheckoutKiosk.GROCERY_ITEMS.length; i++) {
      // check first for the correctness of the getItemName(i) method
      if (!SelfCheckoutKiosk.getItemName(i).equals(SelfCheckoutKiosk.GROCERY_ITEMS[i][0])) {
        System.out.println("Problem detected: Called your getItemName() method with "
            + "input value " + i + ". But it did not return the expected output.");
        return false;
      }

      // Now, let's check for the correctness of the getItemPrice(i) method
      double expectedPriceOutput =
          Double.valueOf(SelfCheckoutKiosk.GROCERY_ITEMS[i][1].substring(1).trim());
      // We do not use == to compare floating-point numbers (double and float)
      // in java. Two variables a and b of type double are equal if the absolute
      // value of their difference is less or equal to a small threshold epsilon.
      // For instance, if Math.abs(a - b) <= 0.001, then a equals b
      if (Math.abs((SelfCheckoutKiosk.getItemPrice(i) - expectedPriceOutput)) > 0.001) {
        // you can print a descriptive error message before returning false
        return false;
      }
    }
    return true; // No defect detected -> The implementation passes this test
  }


  /**
   * Checks the correctness of SelfCheckoutKiosk.addItemToBaggingArea() method
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddItemToBaggingArea() {
    // Create an empty bagging area
    String[] items = new String[10];
    int size = 0;

    // Define the test scenarios:

    // (1) Add one item to an empty bagging area
    // try to add an apple (id: 0) to the bagging area
    size = SelfCheckoutKiosk.addItemToBaggingArea(0, items, size);

    // check if size is right
    if (size != 1) {
      System.out.println("Problem detected: Tried to add one item to an empty "
          + "bagging area. The returned size must be 1. But your addItemToBaggingArea "
          + "method returned a different output.");
      return false;
    }

    // check if add right name
    if (!items[0].equals(SelfCheckoutKiosk.getItemName(0))) {
      // notice here the importance of checking for the correctness of your getItemName()
      // method before calling it above
      System.out.println("Problem detected: Tried to add only one item to an empty "
          + "bagging area. But that item was not appropriately added to the contents "
          + "of the items array.");
    }

    // (2) Consider a non-empty bagging area
    items = new String[] {"Milk", "Chocolate", "Onion", null, null, null, null};
    size = 3;

    // check if size increments correctly
    size = SelfCheckoutKiosk.addItemToBaggingArea(10, items, size);
    if (size != 4) {
      System.out.println("Problem detected: Tried to add only one item to an non-empty "
          + "bagging area. The size must be incremented after the method returns. But "
          + "it was not the case");
      return false;
    }

    // check if add right name
    if (!items[3].equals(SelfCheckoutKiosk.getItemName(10))) {
      System.out.println("Problem detected: Tried to add one item to an non-empty "
          + "bagging area. But that item was not appropriately added to the contents "
          + "of the items array.");
    }

    // (3) Consider adding an item to a full bagging area
    items = new String[] {"Pizza", "Eggs", "Apples"};
    size = 3;
    // copy items to compare modification
    String[] items_cp = new String[size];
    for (int i = 0; i < size; i++) {
      items_cp[i] = items[i];
    }

    size = SelfCheckoutKiosk.addItemToBaggingArea(2, items, size);
    // Check that the returned size is correct (must be 3), and that no
    // changes have been made to the content of items array {"Pizza", "Eggs", "Apples"}

    // check if size not change
    if (size != 3) {
      System.out.println("Problem detected: An item should't be added to a full bagging area. "
          + "The size must not be incremented after the method returns. But "
          + "it was not the case");
      return false;
    }

    // check if items has been modified
    for (int i = 0; i < size; i++) {
      if (items_cp[i] != items[i]) {
        System.out.println("Problem detected: An item cannot be added to a full bagging area. "
            + "The content of items array must not be changed after the method returns. But "
            + "it was not the case");
      }
    }

    return true; // No defects detected by this unit test
  }


  /**
   * Checks the correctness of SelfCheckoutKiosk.count() method
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testCount() {
    String[] items = new String[10];
    int occurNums;
    int size;

    // Define the test scenarios:

    // (1) a bagging area (defined by the items array and its size)
    // which contains 0 occurrences of the item
    items = new String[] {"Milk", "Chocolate", "Onion", null, null, null, null};
    size = 3;

    occurNums = SelfCheckoutKiosk.count("Potato", items, size);

    // check if occurNums is right
    if (occurNums != 0) {
      System.out.println("Problem detected: The item is not in bagging area. "
          + "The returned occurNums must be 0. But your count "
          + "method returned a different output.");
      return false;
    }

    // (2) a bagging area which contains at least 4 items and only one occurrence
    // of the item to count
    items = new String[] {"Milk", "Chocolate", "Onion", "Potato", null, null, null};
    size = 4;

    occurNums = SelfCheckoutKiosk.count("Onion", items, size);

    // check if occurNums is right
    if (occurNums != 1) {
      System.out.println("Problem detected: The item is in bagging area and "
          + "occurs one time. The returned occurNums must be 1. But your count "
          + "method returned a different output.");
      return false;
    }

    // (3) a bagging area which contains at least 5 items
    // and 2 occurrences of the item to count
    items = new String[] {"Milk", "Chocolate", "Onion", "Potato", "Milk", null, null};
    size = 5;

    occurNums = SelfCheckoutKiosk.count("Milk", items, size);

    // check if occurNums is right
    if (occurNums != 2) {
      System.out.println("Problem detected: The item is in bagging area and "
          + "occurs two times. The returned occurNums must be 2. But your count "
          + "method returned a different output.");
      return false;
    }

    return true; // No defect detected -> The implementation passes this test
  }

  /**
   * Checks the correctness of SelfCheckoutKiosk.indexOf() method
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testIndexOf() {
    String[] items = new String[10];
    int idx;
    int size;

    // Define the test scenarios:

    // (1) the item array contain item
    items = new String[] {"Milk", "Chocolate", "Milk", "Onion", "Milk", "Milk"};
    size = 6;

    idx = SelfCheckoutKiosk.indexOf("Milk", items, size);

    // check if idx is right
    if (idx != 0) {
      System.out.println("Problem detected: The item is in the first position of "
          + "the item array. The returned occurNums must be 0. But your indexOf "
          + "method returned a different output.");
      return false;
    }

    // (2) the item was not stored in the array
    items = new String[] {"Milk", "Chocolate", "Milk", "Onion", "Milk", "Milk"};
    size = 6;

    idx = SelfCheckoutKiosk.indexOf("Potato", items, size);

    // check if idx is right
    if (idx != -1) {
      System.out.println("Problem detected: The item is not in the item array."
          + "The returned occurNums must be -1. But your count "
          + "method returned a different output.");
      return false;
    }

    return true; // No defect detected -> The implementation passes this test
  }

  /**
   * Checks the correctness of SelfCheckoutKiosk.remove() method
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRemove() {
    String[] items =
        new String[] {"eggs", "banana", "Avocado", "Milk", "Avocado", null, null, null};
    int size = 5;

    // Define the test scenarios:

    // (1) item exists in item array
    size = SelfCheckoutKiosk.remove("Avocado", items, 5);
    if (size != 4) {
      System.out.println("Problem detected: Tried to remove one item from an non-empty, "
          + "array. The size must be decremented after the method returns. But "
          + "it was not the case");
      return false;
    }

    // (2) item not exists in item array
    items = new String[] {"eggs", "banana", "Avocado", "Milk", "Avocado", null, null, null};
    size = 5;

    size = SelfCheckoutKiosk.remove("Pizza", items, 5);
    if (size != 5) {
      System.out.println("Problem detected: The item is not in the item array. "
          + "The size must remain the same after the method returns. But " + "it was not the case");
      return false;
    }

    // (3) item not exists in item array
    items = new String[] {};
    size = 0;

    size = SelfCheckoutKiosk.remove("Pizza", items, 0);
    if (size != 0) {
      System.out.println("Problem detected: The item array is empty. "
          + "The size must equal to zero after the method returns. But " + "it was not the case");
      return false;
    }
    return true;
  }

  /**
   * Checks the correctness of SelfCheckoutKiosk.getSubTotalPrice() method
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetSubTotalPrice() {

    String[] items = new String[] {"Milk", "Chocolate", "Onion", null, null, null, null};
    int size = 3;
    double assumedTotal, total;
    assumedTotal = SelfCheckoutKiosk.getSubTotalPrice(items, size);
    total = (Double.valueOf(SelfCheckoutKiosk.GROCERY_ITEMS[17][1].substring(1).trim())
        + Double.valueOf(SelfCheckoutKiosk.GROCERY_ITEMS[11][1].substring(1).trim())
        + Double.valueOf(SelfCheckoutKiosk.GROCERY_ITEMS[19][1].substring(1).trim()));
    if (Math.abs((assumedTotal - total)) > 0.001) {
      System.out.println("Problem detected: For the test case, the value computed by "
          + "the function is different with the real value.");
      System.out.println(assumedTotal + " " + total);
      return false;
    }

    return true;
  }

  /**
   * Checks the correctness of SelfCheckoutKiosk.getUniqueCheckedOutput() method
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetUniqueCheckedOutItems() {
    String[] items =
        new String[] {"eggs", "banana", "Avocado", "eggs", "Milk", "Avocado", "Milk", null, null};
    int size = 7;
    String[] itemsSet = new String[size];
    size = SelfCheckoutKiosk.getUniqueCheckedOutItems(items, size, itemsSet);
    if (size != 4) {
      System.out.println("Problem detected: Tried to remove duplicate item from an non-empty "
          + "array. The size must be decremented after the method returns. But "
          + "it was not the case");
      return false;
    }
    return true;
  }

  /**
   * main method used to call the unit tests
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out
        .println("tesItemNameAndPriceGetterMethods(): " + testItemNameAndPriceGetterMethods());
    System.out.println("testAddItemToBaggingArea(): " + testAddItemToBaggingArea());
    System.out
        .println("tesItemNameAndPriceGetterMethods(): " + testItemNameAndPriceGetterMethods());
    System.out.println("testCount(): " + testCount());
    System.out.println("testIndexOf(): " + testIndexOf());
    System.out.println("testRemove(): " + testRemove());
    System.out.println("testGetSubTotalPrice(): " + testGetSubTotalPrice());
    System.out.println("testGetUniqueCheckedOutItems(): " + testGetUniqueCheckedOutItems());
    System.out.println("test printCatalog(): ");
    SelfCheckoutKiosk.printCatalog();
  }
}
