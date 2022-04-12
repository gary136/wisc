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
// Online Sources:
// string format -
// https://dzone.com/articles/java-string-format-examples
//
///////////////////////////////////////////////////////////////////////////////

/**
 * Implement the details how a self-check-out kiosk should work
 */
public class SelfCheckoutKiosk {

  /* constants */
  public static final double TAX_RATE = 0.05;

  public static final String[][] GROCERY_ITEMS = new String[][] {{"Apple", "$1.59"},
      {"Avocado", "$0.59"}, {"Banana", "$0.49"}, {"Beef", "$3.79"}, {"Blueberry", "$6.89"},
      {"Broccoli", "$1.79"}, {"Butter", "$4.59"}, {"Carrot", "$1.19"}, {"Cereal", "$3.69"},
      {"Cheese", "$3.49"}, {"Chicken", "$5.09"}, {"Chocolate", "$3.19"}, {"Cookie", "$9.5"},
      {"Cucumber", "$0.79"}, {"Eggs", "$3.09"}, {"Grape", "$2.29"}, {"Ice Cream", "$5.39"},
      {"Milk", "$2.09"}, {"Mushroom", "$1.79"}, {"Onion", "$0.79"}, {"Pepper", "$1.99"},
      {"Pizza", "$11.5"}, {"Potato", "$0.69"}, {"Spinach", "$3.09"}, {"Tomato", "$1.79"}};

  /* functions */
  /**
   * Returns the name of the item given its index
   * 
   * @param index - unique identifier of an item
   * @return the name of item
   */
  public static String getItemName(int index) {
    return GROCERY_ITEMS[index][0];
  }

  /**
   * Returns the price of an item given its index (unique identifier)
   * 
   * @param index unique identifier of an item
   * @return the price of item
   */
  public static double getItemPrice(int index) {
    String unformatted_price = GROCERY_ITEMS[index][1];
    return Double.valueOf(unformatted_price.substring(1).trim());
  }

  /**
   * Prints the Catalog of the grocery store (item identifiers, names, and prices)
   */
  public static void printCatalog() {
    // Complete the missing code /* */ in the following implementation
    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
    System.out.println("Item id \tName \t Price");
    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
    for (int i = 0; i < GROCERY_ITEMS.length; i++) {
      System.out.println(i + "\t\t" + getItemName(i) + " \t " + "$" + getItemPrice(i));
      // not use due to suggestion
      // make the names printed with same length
      // System.out.format(i + "\t\t" + "%-15s" + "$" +
      // getItemPrice(i) + "\n", getItemName(i));
    }
    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
  }

  /**
   * Adds the name of a grocery item at the end of the bagging area. If the items array reaches its
   * capacity, print: "Error! No additional item can be scanned. Please wait for assistance." and
   * returns without changing the contents of the items array.
   * 
   * @param id    identifier of the item to be added to the bagging area
   * @param items the bagging area, array storing the names of the items checked out and placed
   * @param size  number of elements stored in items before trying to add a new item
   * @return the number of elements stored in bagging area after adding the item
   */
  public static int addItemToBaggingArea(int id, String[] items, int size) {
    // check if capacity is full
    if (items.length == size) {
      System.out.println("Error! No additional item can be scanned. Please wait for assistance.");
      return size;
    }

    // existing item covers from [0] to [size-1], so the new item
    // should be placed at [size]
    items[size] = getItemName(id);
    return size + 1;
  }

  /**
   * Returns the number of occurrences of a given item in an oversize array of strings. The
   * comparison is case insensitive.
   * 
   * @param item  item to count its occurrences
   * @param items a bag of string items
   * @param size  number of items stored in items
   * @return the number of occurrences of a given item
   */
  public static int count(String item, String[] items, int size) {
    int num = 0; // the number to increment
    for (int i = 0; i < size; i++) {
      if (item.equalsIgnoreCase(items[i])) {
        num++;
      }
    }
    return num;
  }

  /**
   * Returns the index of the first occurrence of item in items if found, and -1 if the item not
   * found
   * 
   * @param item  element to search for
   * @param items an array of string elements
   * @param size  number of elements stored in items
   * @return index of the first occurrence if found and -1 if not found
   */
  public static int indexOf(String item, String[] items, int size) {
    for (int i = 0; i < size; i++) {
      if (item.equals(items[i])) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Removes the first occurrence of itemToRemove from the bagging area. If no match is found, print
   * "WARNING: item not found." without changing the item array. This method compacts the contents
   * of the items array after removing the itemToRemove so there are no empty spaces in the middle
   * of the array.
   * 
   * @param itemToRemove item to remove from the bagging area
   * @param items        a bag of items
   * @param size         number of elements stored in the bag of items
   * @return the number of items present in the cart after removal
   */
  public static int remove(String itemToRemove, String[] items, int size) {
    boolean find = false; // use as a flag to control the process

    // check if the item array is empty
    if (size == 0) {
      return size;
    }

    for (int i = 0; i < size; i++) {
      if (find) {
        // upon finding target, will not change the content until the next round
        // continue to copy element to the position of last index
        // this causes temporary duplicate
        items[i - 1] = items[i];
        // the last element should be set to null to prevent duplicate
        if (i == size - 1) {
          items[i] = null;
        }
      }
      // check if there is element having same value with itemToRemove
      if (itemToRemove.equals(items[i])) {
        find = true;
      }
    }
    // if itemToRemove is not in items
    if (!find) {
      System.out.println("WARNING: item not found.");
      return size;
    } else {
      return size - 1;
    }
  }

  // Helper function of getUniqueCheckedOutItems
  /**
   * Checks whether an array contains a specific string
   * 
   * @param toCheck the string we want to check if existing in the array
   * @param arr     the array we want to check
   * @param size    number of elements stored in the array
   * @return true if toCheck is found in the array
   */
  private static boolean checkDuplicate(String toCheck, String[] arr, int size) {
    for (int i = 0; i < size; i++) {
      if (toCheck == arr[i] && toCheck != null) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets a copy of the items array to the itemsSet array without duplicates. The itemsSet array is
   * initially empty. The contents of the items array does not change.
   * 
   * @param items    list of items added to the bagging area
   * @param size     an number of elements stored in items
   * @param itemsSet an empty array for copying unique items
   * @return an number of elements stored in itemsSet
   */
  public static int getUniqueCheckedOutItems(String[] items, int size, String[] itemsSet) {
    // Note that we assume that the length of itemsSet equals
    // at least the size of items. This means that itemsSet array
    // can store the set of scanned items at checkout
    int itemsSetSize = size;
    int itemsIndex;
    int itemsSetIndex = 0;

    for (itemsIndex = 0; itemsIndex < size; itemsIndex++) {
      if (!checkDuplicate(items[itemsIndex], itemsSet, size)) {
        // add item to itemsSet if item is not in itemsSet
        itemsSet[itemsSetIndex] = items[itemsIndex];
        // increment itemsSetIndex since the position of current index is used
        itemsSetIndex++;
      } else {
        // when item is already in itemSet,
        // no item will be added, consequently reduce the itemSetSize
        itemsSetSize--;
      }
    }
    return itemsSetSize;
  }

  /**
   * Returns the total price of the scanned items without tax in $ double
   * 
   * @param items an array which stores the items checked out
   * @param size  number of elements stored in the items array
   * @return the total price without tax
   */
  public static double getSubTotalPrice(String[] items, int size) {
    double total = 0.00; // this will be incremented after the price is got 
    int tmpIndex;
    double price;

    // create a mapping list storing all item names
    String[] GROCERY_NAME_ITEMS = new String[GROCERY_ITEMS.length];
    for (int i = 0; i < GROCERY_ITEMS.length; i++) {
      GROCERY_NAME_ITEMS[i] = getItemName(i);
    }

    for (int i = 0; i < size; i++) {
      // use indexOf as the helper function
      // to get the index each element in items would map to
      tmpIndex = indexOf(items[i], GROCERY_NAME_ITEMS, GROCERY_NAME_ITEMS.length);
      // the index here equals to the index in GROCERY_ITEMS
      price = getItemPrice(tmpIndex);
      total += price;
    }
    return total;
  }
}
