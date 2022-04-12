///////////////////////// CUMULATIVE QUIZ FILE HEADER //////////////////////////
// Full Name: 
// Campus ID: 
// WiscEmail: 
////////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;

/**
 * This class models Book objects which can be added to a Shelf. Each object contains its
 * page and chapter count information, and is sortable by increasing AVERAGE chapter length value 
 * (that is, the number of pages divided by the number of chapters).
 * 
 * TODO: Modify this class to implement the Comparable interface, so that a Book object can
 * be compared to another Book object without casting.
 */
class Book implements Comparable<Book>{
  // instance fields
  private String name;  // the name of this book, for display
  private int pages;    // the book's page count
  private int chapters; // the books chapter count
  
  /**
   * Create a new Book object
   * 
   * @param name the name of the book
   * @param pages the book's page count
   * @param chapters the book's chapter count
   * @throws IllegalStateException if any of the integer arguments are negative or zero
   */
  public Book(String name, int pages, int chapters) throws IllegalStateException{
    
    // TODO: If given an illegal (negative or zero) value for any of the integers, throw an
    // IllegalStateException instead of completing the construction
    if (pages <= 0 || chapters <= 0) throw new IllegalStateException("arguments are negative or zero");
    // TODO: Complete the implementation of this constructor to initialize all the instance fields
    this.name=name;
    this.pages=pages;
    this.chapters=chapters;
  }
  
  /**
   * Produces a formatted String representation of this Book
   * 
   * For example, the book "2001" with pages=297 and chapters=47 would produce the String 
   * "2001: 297p/47c"
   * 
   * @return the formatted book String
   */
  @Override
  public String toString() {
    
    // TODO: complete this implementation according to the javadoc comment above!
    
    return this.name + ": " + this.pages + "p/" + this.chapters + "c";       
  }
  
  
  // TODO: add any methods required by the Comparable interface here.
  
  // Book objects will be compared with respect to their average chapter length in pages:
  
  // - One Book is "greater than" another Book if its average chapter length is a
  //   larger number than the other's.
  // - Two Book items are equal if they have the same average chapter length.
  // - One Book is "less than" another Book if its average chapter length is a smaller
  //   number than the other's.
  
  // Note: MAKE SURE TO SAVE your source file before uploading it to gradescope.
  

  ////////////////////////////////////////////////////////////////////////////////
  ////////// ACCESSOR METHODS BELOW THIS LINE DO NOT NEED TO BE MODIFIED /////////
  ////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Access the name of this Book
   * @return the Book's name
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * Access the page count of this Book
   * @return the number of pages in this Book
   */
  public int getPages() {
    return this.pages;
  }
  
  /**
   * Access the chapter count of this Book
   * @return the number of chapters in this Book
   */
  public int getChapters() {
    return this.chapters;
  }

  @Override
  public int compareTo(Book o) {
    // TODO Auto-generated method stub
    if ((this.pages/this.chapters)>(o.pages/o.chapters)) return 1;
    else if ((this.pages/this.chapters)==(o.pages/o.chapters)) return 0;
    return -1;
  }
}

