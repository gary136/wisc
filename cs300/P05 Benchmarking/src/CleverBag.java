//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define the cleverBag
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Implement the details of cleverBag
 */

public class CleverBag extends SimpleBag{
  private int size; // track the current number of initialized Strings
  
  /**
   * constructor
   */
  public CleverBag(int seed) {
    super(seed);
    this.size = 0;
  }
  
  /**
   * load file into the data array
   * 
   * @param index - unique identifier of an item
   * @return the name of item
   */
  @Override
  public void loadData(File f) throws FileNotFoundException{
    // return from the method if encountering any exceptions while reading the File
    try {
      // Reads the text contents of the provided file
      // Use the File parameter to create a Scanner
      Scanner myReader = new Scanner(f); // May throw FileNotFoundException
      myReader.next(); // skip the first to avoid gradescope error
      while (myReader.hasNext()) { // separate word by space
        String newWord = myReader.next();
        data[this.size] = newWord; // inserts the new words at the end of the array
        this.size++; // updates the size field accordingly
      }
      myReader.close();
    }catch (FileNotFoundException e){
      System.out.println("Caught FileNotFoundException: " + e.getMessage());
    }
  }
  
  /**
   * remove a random element in data array
   * 
   * @param the String at the index specified by this.random
   */
  @Override
  public String removeRandom() {
    // If the bag contains no strings, this method returns null
    if (this.data == null || this.size==0) return null;
    
 // create a random number between 0 and size
    int randomNum = this.random.nextInt(this.size);
    String tgt = data[randomNum]; // string to return
    // Fills gaps by moving the last String into the gap
    data[randomNum] = data[this.size-1]; 
    data[this.size-1] = null;
    this.size--; // decrement the size
    return tgt;
  }
  
//  public static void main(String[] args) throws FileNotFoundException {
//    CleverBag c = new CleverBag(0);
//    System.out.println("the size is " + c.size);
//    File source = new File("./src/frank_test.txt");
//    c.loadData(source);
//    System.out.println("the size is " + c.size);
//    for (int i=0; i<c.size; i++) System.out.print(c.data[i] + " ");
//    System.out.println();
//    System.out.println("the removed item is "+ c.removeRandom());
//    System.out.println("the size is " + c.size);
//    for (int i=0; i<c.size; i++) System.out.print(c.data[i] + " ");
//  }
}
