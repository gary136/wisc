//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define the simpleBag
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
import java.util.Random;
import java.util.Scanner;

/**
 * Implement the details of simpleBag
 */

public class SimpleBag {
  protected String[] data; // oversize array
//  protected int notNullElemNums; // cannot create unspecified instance variable
  protected Random random;
  
  /**
   * constructor
   * 
   * @param seed - specify a seed to create a random number
   */
  public SimpleBag(int seed) {
    // a protected field, data, which is an array of Strings with capacity 80,000
    this.data = new String[80000]; 
    // a protected Random object,random, using the provided seed value
    this.random = new Random(seed);
  }
  
  /**
   * load file into the data array
   * 
   * @param f - the file to load
   */
  public void loadData(File f) throws FileNotFoundException{
    int notNullElemNums = 0;
    
    // return from the method if encountering any exceptions while reading the File
    try {
      // Reads the text contents of the provided file
      // Use the File parameter to create a Scanner
      Scanner myReader = new Scanner(f); // May throw FileNotFoundException
      myReader.next(); // skip the first to avoid gradescope error
      while (myReader.hasNext()) {
        String newWord = myReader.next(); // separate word by space
        // All strings currently in the array should be shifted 
        // to the right by one index to make room
        for (int i=notNullElemNums; i>=0; i--) {
          data[i+1]=data[i];
        }
        notNullElemNums++; // size
        // insert at the beginning of the data array
        data[0] = newWord;
      }
      
      myReader.close();
    }catch (FileNotFoundException e){
      System.out.println("Caught FileNotFoundException: " + e.getMessage());
    }
  }
  
  /**
   * remove a random element in data array
   * 
   * @return the String at the index specified by this.random
   */
  public String removeRandom() {
    int notNullElemNums = 0;
    // count how many elem is not null in data 
    while (data[notNullElemNums]!=null) notNullElemNums++;
    
    // If the bag contains no strings, this method returns null
    if (this.data == null || notNullElemNums==0) return null;
    
    // create a random number between 0 and notNullElemNums
    int randomNum = this.random.nextInt(notNullElemNums);
    String tgt = data[randomNum]; // string to return
    for (int i=randomNum; i<notNullElemNums; i++) {
      // Fills gaps by moving all following strings to the left 
      // by one index. N -> N-1
      data[i]=data[i+1];
    }
    return tgt;
  }
  
//  public static void main(String[] args) throws FileNotFoundException {
//    SimpleBag a = new SimpleBag(0);
//    int notNullElemNums = 0;
//    // count how many elem is not null in data 
//    while (a.data[notNullElemNums]!=null) notNullElemNums++;
//    System.out.println("the notNullElemNums is " + notNullElemNums);
//    File source = new File("./src/frank_test.txt");
//    a.loadData(source);
//    notNullElemNums = 0;
//    // count how many elem is not null in data 
//    while (a.data[notNullElemNums]!=null) notNullElemNums++;
//    System.out.println("the notNullElemNums is " + notNullElemNums);
//    for (int i=0; i<notNullElemNums; i++) System.out.print(a.data[i] + " ");
//    System.out.println();
//    System.out.println("the removed item is "+ a.removeRandom());
//    notNullElemNums = 0;
//    // count how many elem is not null in data 
//    while (a.data[notNullElemNums]!=null) notNullElemNums++;
//    System.out.println("the notNullElemNums is " + notNullElemNums);
//    for (int i=0; i<notNullElemNums; i++) System.out.print(a.data[i] + " ");
//  }
}
