//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define the Benchmark
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
import java.io.FileWriter;
import java.io.IOException;

/**
 * Implement the details of Benchmark
 */

public class Benchmark {
  
  /**
   * compare the elapsed times between different version of loadData()
   * 
   * @param f - the file to load
   * @param s - a SimpleBag object
   * @param c - a CleverBag object
   * @return a formatted String with the elapsed times for each of the bag types
   */
  public static String compareLoadData(File f, SimpleBag s, CleverBag c) {
    // Use theSystem.currentTimeMillis() method to 
    // get current system time in milliseconds
    long simpleA = System.currentTimeMillis();
    try {
      s.loadData(f); // Runs SimpleBag’s loadData() implementations
    } catch (FileNotFoundException e) {
      System.out.println("Caught FileNotFoundException: " + e.getMessage());
    }
    long simpleB = System.currentTimeMillis();
    long simpleBagLoadTime = simpleB - simpleA; // Tracks the time spent in milliseconds
    
    // check content
//    System.out.println("simple data check");
//    for (int i=0; i<s.notNullElemNums; i++) 
//        System.out.print(s.data[i] + " ");
//    System.out.println();
    
    long cleverA = System.currentTimeMillis();
    try {
      c.loadData(f); // Runs CleverBag’s loadData() implementation
    } catch (FileNotFoundException e) {
      System.out.println("Caught FileNotFoundException: " + e.getMessage());
    }
    long cleverB = System.currentTimeMillis();
    long cleverBagLoadTime = cleverB - cleverA; // Tracks the time spent in milliseconds
    
    // check content
//    System.out.println("clever data check");
//    for (int i=0; i<s.notNullElemNums; i++) 
//        System.out.print(c.data[i] + " ");
//    System.out.println();
    
    return "load:\t"+ simpleBagLoadTime +"\t"+ cleverBagLoadTime+"\n";
  }
  
  /**
   * compare the elapsed times between different version of removeRandom()
   * 
   * @param n - specify how many times the method would run
   * @param s - a SimpleBag object
   * @param c - a CleverBag object
   * @return a formatted string with n and the elapsed times for each of the bag types
   */
  public static String compareRemove(int n, SimpleBag s, CleverBag c) {
    long simpleA = System.currentTimeMillis();
    for (int i=0; i<n; i++) { // Runs SimpleBag’s removeRandom() method n times 
      s.removeRandom();
    }
    long simpleB = System.currentTimeMillis();
    long simpleBagLoadTime = simpleB - simpleA;
    
    long cleverA = System.currentTimeMillis();
    for (int i=0; i<n; i++) { // Runs CleverBag’s removeRandom() method n times
      c.removeRandom();
    }
    long cleverB = System.currentTimeMillis();
    long cleverBagLoadTime = cleverB - cleverA;
    return n+"\t"+ simpleBagLoadTime +"\t"+ cleverBagLoadTime+"\n";
  
  }
  
  /**
   * creates the actual objects, 
   * passes them to each of the compare methods, 
   * and writes the result to a file
   * 
   * @param in - the input file
   * @param out - where the result is stored
   * @param nValues - an array to specify how many times should run
   */
  public static void createResultsFile(File in, File out, int[] nValues) throws IOException {

    // Creates one instance each of a SimpleBag and a CleverBag
    SimpleBag s = new SimpleBag(0);
    CleverBag c = new CleverBag(0);
    String removeStat = ""; // a string to be incremented in compareRemove
    // Calls compareLoadData() to compare the two different data loads using File in
    String loadDataStat = compareLoadData(in, s, c);
    // Calls compareRemove() on each of the provided nValues 
    // to compare the two different remove implementations
    for (int i = 0; i<nValues.length; i++) {
      removeStat += compareRemove(nValues[i], s, c);
    }
    
    // Writes the results of the data load comparison followed 
    // by the remove comparisons to a file specified by File out
    FileWriter writer = new FileWriter(out); 
    writer.write(loadDataStat); 
    writer.write(removeStat); 
    writer.flush();
    writer.close();
  }
  
  public static void main(String[] args) throws IOException, IllegalArgumentException {
    SimpleBag s = new SimpleBag(0);
    CleverBag c = new CleverBag(0);
    int[] nRange = {10, 100, 1000, 10000};
    File source = new File("./src/frank.txt");
    File output = new File("./src/xxx.txt");
    System.out.println(compareLoadData(source, s, c));
    System.out.println(compareRemove(4, s, c));
    try { // handle any exceptions
      createResultsFile(source, output, nRange);
    } catch (IOException e1) {
      System.out.println("Caught IOException: " + e1.getMessage());
    }catch (IllegalArgumentException e2) {
      System.out.println("Caught IllegalArgumentException: " + e2.getMessage());
    }
  }
  
}
