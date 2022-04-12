/////////////////////////////// EXAM FILE HEADER ///////////////////////////////
//Full Name: Lee Hung Ting
//Campus ID: 9083057233
//WiscEmail: hlee864@wisc.edu
////////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

/**
 * The Shelf class contains various Book objects and offers utility methods for analyzing
 * the contents of the shelf.
 * 
 * Note: DO NOT add any additional data fields! You may add private helper methods if needed.
 */
public class Shelf {
  // instance field
  private ArrayList<Book> contents;   // list of all the Books included on this shelf
  private final int PAGE_LIMIT;      // maximum page count for the shelf
  
  /**
   * Constructor: initializes the Shelf's contents and sets the upper limit for total page count
   * @param pageLim the upper limit for page count for the shelf
   * @throws IllegalStateException if the page count limit is zero or negative
   */
  public Shelf(int pageLim) throws IllegalStateException{
    // TODO: initialize the Meal's instance field and constant, or throw an exception
    contents = new ArrayList<Book>();
    if (pageLim<=0) throw new IllegalStateException("invalid argument");
    this.PAGE_LIMIT=pageLim;
//    PAGE_LIMIT = -1; // change this
  }
  
  // mutators
  /**
   * Adds a Book to the Shelf IF AND ONLY IF the total page count limit is not exceeded
   * @param b the Book to add
   * @return true if the Book was added successfully, false otherwise
   */
  public boolean addBook(Book b) {
    // TODO: check if adding the new Book to the menu will *exceed* the PAGE_LIMIT. 
    int currTotal = this.getTotalPages();
//    System.out.println(this.PAGE_LIMIT);
    // If it will NOT exceed the limit, add the new Book to the shelf and return true.
    if (currTotal+b.getPages()<=this.PAGE_LIMIT) {
      this.contents.add(b); 
      return true;
    }
    // If it will EQUAL the limit, add the new Book to the shelf and return true.
    
    // If it will exceed the limit, do not add the Book; return false.
    else
      return false; // change this
  }
  
  /**
   * Removes the Book with the shortest average chapter length from the Shelf. If the Shelf is 
   * empty, returns null.
   * @return the Book with the shortest average chapter length.
   */
  public Book removeShortest() {
    // TODO: remove the shortest average chapter length Book from this Shelf.
    // Once you have implemented the Comparable interface in the Book class, uncomment the
    // private sort() helper method below and use it to sort this Shelf's contents from shortest to
    // longest average chapter length.
    // Then remove and return the shortest average chapter length Book.
    Book currShortest = this.contents.get(0);
    //  System.out.println(currTotal); //this.contents.size()
    for (int i=1; i<this.contents.size(); i++){
      if (this.contents.get(i).getPages()<currShortest.getPages())
        currShortest = this.contents.get(i);
  }
    return currShortest; // change this
  }
  
  /**
   * Private helper method, uses Collections.sort and the Comparable interface to sort the contents
   * in increasing order.
   * TODO: Uncomment this method after implementing Comparable in Book
   */

  /*
  private void sortShelf() {
    java.util.Collections.sort(this.contents);
  }
  */
  
  // accessors
  /**
   * Calculates the total page count of the Shelf
   * @return the total number of pages across all Book objects on this Shelf
   */
  public int getTotalPages() {
    // TODO: return the total page count of this Shelf's contents
    
    int currTotal = 0;
//    System.out.println(currTotal); //this.contents.size()
    for (int i=0; i<this.contents.size(); i++){
        currTotal+=this.contents.get(i).getPages();
    }
    return currTotal; // change this
  }

  // Note: MAKE SURE TO SAVE your source file before uploading it to gradescope.
  
  /**
   * String representation of the Menu, provided for your testing benefit
   */
  @Override
  public String toString() {
    String retval = "Book List ("+getTotalPages()+"p):\n";
    for (Book b : contents) {
      retval += "  " + b + "\n";
    }
    return retval;
  }

  /**
   * Main method for YOUR testing purposes, modify as you like. Not tested by us.
   * @param args unused
   */
  public static void main(String[] args) {
    Shelf s = new Shelf(705);
    Book b1 = new Book("2001", 297, 47);
    Book b2 = new Book("Dungeon World", 408, 34);
    Book b3 = new Book("White Rage", 285, 7);
    
    s.addBook(b1); // should work
//    System.out.println(s.contents.size());
//    System.out.println(s.getTotalPages());
    s.addBook(b2); // should work
//    System.out.println(s.contents.size());
//    System.out.println(s.getTotalPages());
    s.addBook(b3); // should NOT work
//    System.out.println(s.contents.size());
//    System.out.println(s.getTotalPages());
    
    System.out.println(s);
    
    System.out.println("Largest: "+s.removeShortest()); // should be 2001
    System.out.println();
    
    s.addBook(b3); // should work
    System.out.println(s);
  }

  // Note: MAKE SURE TO SAVE your source file before uploading it to gradescope!!
}