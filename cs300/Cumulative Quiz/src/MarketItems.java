/////////////////////////////// EXAM FILE HEADER ///////////////////////////////
//Full Name: Lee Hung Ting
//Campus ID: 9083057233
//WiscEmail: hlee864@wisc.edu
////////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

// Complete the missing (blank) sections of code in this MarketItems class.
// This class implements a binary search tree to store all items available in a given market
// Note that the comparison of the Item objects is based on the increasing order of their IDs.
// This means that this BST is ordered with respect to the IDs of their entries
// Any exceptions thrown do not need messages.
// You are NOT allowed to add any instance field not already defined in the starting code of this
// MarketItems class.
// You are NOT allowed to make any change to the signatures of the provided methods.


/**
 * This class represents a BST of Item objects. The search key in this BST is the name of the
 * entries.
 */
public class MarketItems {
  private BSTNode<Item> root; // see exam reference section listing for BSTNode<T>

  /**
   * Checks whether this BST is empty
   * 
   * @return true if this BST is empty and false otherwise
   */
  public boolean isEmpty() {
    if (root==null) return true;
    return false; // TODO CHANGE THIS: included to avoid compiler errors
  }

  // MAKE SURE TO SAVE your source file before uploading it to gradescope.
  /**
   * Recursive helper method to add a new item into this market items BST
   * 
   * @param newItem the new item being added
   * @param node    root of the subtree that newItem is being inserted into
   * @return the new root of this subtree after newItem is inserted
   * @throws IllegalArgumentException when this MarketItems BST contains a duplicate entry with
   *                                  newItem
   */
  protected static BSTNode<Item> addHelper(Item newItem, BSTNode<Item> node) throws IllegalArgumentException{
    // DO NOT CHECK whether newItem is null or not. The method will automatically throw
    // a NullPointerException if this case occurs.

    // TODO complete the implementation of this method
    
    // base case1: the current subtree is empty (node is null) --> add newItem, and
    // return the new root (node) of this subtree

    BSTNode<Item> newX = new BSTNode(newItem);
    
    if (node==null) {
      return newX;
    }

    // base case2: subtree contains a match with node

    // recursive case: recurse left
    // try to insert newItem to the left subtree 
    // Hint: node.setLeft(/* recursive call */);

    // else recursive case: recurse right
    // try to insert newItem to the right subtree
    // Hint: node.setRight(/* recursive call */);
    if (newX.getData().compareTo(node.getData())<0 && node.getLeft()!=null) node.setLeft(newX);
    if (newX.getData().compareTo(node.getData())>0 && node.getRight()!=null) node.setLeft(newX);
    else {
      if (newX.getData().compareTo(node.getData()) < 0) 
        return addHelper(newItem, node.getLeft());
      else
        return addHelper(newItem, node.getRight());
    }
    return node; // return the root of this subtree
  }

  /**
   * Adds a new item entry into this BST.
   * 
   * @param newItem is the data being inserted into this market items BST
   * @throws IllegalArgumentException when this BST already contains a duplicate entry with newItem
   */
  public void add(Item newItem) {
    root = addHelper(newItem, root);
  }

  /**
   * Returns the number of item objects stored in this BST
   * 
   * @return the size of this BST
   */
  public int size() {
    return sizeHelper(root); 
  }

  /**
   * Recursively computes and returns the size of the BST rooted at node
   * 
   * @param node root of the subtree whose size is being computed
   * @return the size of the subtree rooted at node
   */
  protected static int sizeHelper(BSTNode<Item> node) {
    // TODO Complete this method
    if (node==null) {
      return 0;
    }
    // TODO Complete the implementation of this method.
    int l = sizeHelper(node.getLeft());
    int c = 1;
    int r = sizeHelper(node.getRight());
    int depth = (l>r) ? l : r;
    return c + depth; // remove this statement.
  }

  // MAKE SURE TO SAVE your source file before uploading it to gradescope.

  /**
   * Finds the name and price of an item given its unique identifier
   * 
   * @param id the unique identifier of the item to find its name and price
   * @return The pair name and price of the item to find in the format "name(price)"
   * @throws NoSuchElementException when no entry in this BST has that identifier
   */
  public String find(int id) {
    return findHelper(id, root);
  }

  /**
   * Recursively searches for the name and price of an item given its unique identifier in the binary
   * search tree rooted at node
   * 
   * @param id    unique identifier of the item to find
   * @param node root of the subtree that is being searched
   * @return The pair name and price of the item to find in the format "name(price)"
   * @throws NoSuchElementException when no item in the BST rooted at node has that identifier
   */
  protected String findHelper(int id, BSTNode<Item> node) throws NoSuchElementException{
    // TODO Complete this method
    
//    if (isEmpty()) return false;
    if (node==null) {
      throw new NoSuchElementException("no such element");
    }
    
    // equal
    if (node.getData().getID()==id) return node.getData().getName()+"("+node.getData().getPrice()+")";
    
    // less 
    if (node.getData().getID()<id) {
      return findHelper(id, node.getLeft());
    }
    
    // greater 
    if (node.getData().getID()>id) {
      return findHelper(id, node.getRight());
    }
    
    return "";
  }


  // MAKE SURE TO SAVE your source file before uploading it to gradescope.

  /**
   * Checks the correctness of insert(), search(), and size() methods
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddFindSize() {
    Item i1 = new Item ("Milk", 2.5);
    Item i2 = new Item ("Eggs", 2.0);
    Item i3 = new Item ("Apples", 3.5);
    Item i4 = new Item ("Grapes", 5.0);
    Item i5 = new Item ("Yogurt", 4.5);
    
    MarketItems items = new MarketItems();
    try {
      items.add(i1);
      if (items.size() != 1 || !items.find(1).equals("Milk(2.5)")) {
        return false;
      }

      items.add(i2);
      if (items.size() != 2 || !items.find(2).equals("Eggs(2.0)")) {
        return false;
      }

      items.add(i3);
      if (items.size() != 3 || !items.find(3).equals("Apples(3.5)")) {
        return false;
      }

      items.add(i4);
      if (items.size() != 4) {
        return false;
      }

      items.add(i5);
      if (items.size() != 5 || !items.find(4).equals("Grapes(5.0)")
          || !items.find(5).equals("Yogurt(4.5)")) {
        return false;
      }

      try {
        items.find(10);
        return false;
      } catch (NoSuchElementException e) {

      }

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  /**
   * Main method
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println("testAddFindSize(): " + testAddFindSize());
  }
}
