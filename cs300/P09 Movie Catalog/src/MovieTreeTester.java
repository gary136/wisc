//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define MovieTreeTester
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

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class checks the correctness of the implementation of the methods 
 * defined in the class MovieTree
 *
 */

public class MovieTreeTester {

  /**
   * Checks the correctness of the implementation of both addMovie() and toString() methods
   * implemented in the MovieTree class. This unit test considers at least the following scenarios.
   * (1) Create a new empty MovieTree, and check that its size is 0, 
   * it is empty, and that its string representation is an empty string "". 
   * (2) try adding one movie and then check that the addMovie() 
   * method call returns true, the tree is not empty, its size is 1, 
   * and the .toString() called on the tree returns the expected output. 
   * (3) Try adding another movie which is smaller that the movie at the root, 
   * (4) Try adding a third movie which is greater than the one at the root, 
   * (5) Try adding at least two further movies such that one must be added 
   * at the left subtree, and the other at the right subtree. 
   * For all the above scenarios, and more, double check each time that 
   * size() method returns the expected value, the add method call returns
   * true, and that the .toString() method returns the expected string 
   * representation of the contents of the binary search tree in 
   * an increasing order from the smallest to the greatest movie with respect to 
   * year, rating, and then name. 
   * (6) Try adding a movie already stored in the tree. 
   * Make sure that the addMovie() method call returned false, 
   * and that the size of the tree did not change.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddMovieToStringSize() {
    // (1) Create a new empty MovieTree, and check that its size is 0, 
    // it is empty, and that its string representation is an empty string "". 
    MovieTree t1 = new MovieTree();
    if (t1.size()!=0) { System.out.println("size fails"); return false; }
    if (!t1.isEmpty()) { System.out.println("isEmpty fails"); return false; }
    if (t1.toString()!="") { System.out.println("toString fails"); return false; }
    
    // (2) try adding one movie and then check that the addMovie() 
    // method call returns true, the tree is not empty, its size is 1, 
    // and the .toString() called on the tree returns the expected output. 
    if (!t1.addMovie(new Movie(1950, 4, "Whatever"))) { 
      System.out.println("addMovie fails"); return false; 
    }
    if (t1.size()!=1) { System.out.println("size fails"); return false; }
    if (t1.isEmpty()) { System.out.println("isEmpty fails"); return false; }
    System.out.println(t1);
//    if (!t1.toString().equals("[(Year: 1950) (Rate: 4.0) (Name: Whatever)]")) { 
//      System.out.println("toString fails"); return false; 
//    }
    
    // (3) Try adding another movie which is smaller that the movie at the root,
    if (!t1.addMovie(new Movie(1930, 4, "Metropolotis"))) { 
      System.out.println("addMovie fails"); return false; 
    }
    if (t1.size()!=2) { System.out.println("size fails"); return false; }
    System.out.println(t1);    
    
    // (4) Try adding a third movie which is greater than the one at the root
    if (!t1.addMovie(new Movie(1965, 4, "Adventure"))) { 
      System.out.println("addMovie fails"); return false; 
    }
    if (t1.size()!=3) { System.out.println("size fails"); return false; }
    System.out.println(t1);
    
    // 5) Try adding at least two further movies such that one must be added 
    // at the left subtree, and the other at the right subtree.
    if (!t1.addMovie(new Movie(1940, 1, "Game"))) { 
      System.out.println("addMovie fails"); return false; 
    }
    if (t1.size()!=4) { System.out.println("size fails"); return false; }
    System.out.println(t1);
    if (!t1.addMovie(new Movie(1970, 4, "Shark"))) { 
      System.out.println("addMovie fails"); return false; 
    }
    if (t1.size()!=5) { System.out.println("size fails"); return false; }
    System.out.println(t1);

    // (6) Try adding a movie already stored in the tree. 
    // Make sure that the addMovie() method call returned false, 
    // and that the size of the tree did not change.
    if (t1.addMovie(new Movie(1940, 1, "Game"))) { 
      System.out.println("addMovie fails"); return false; 
    }
    if (t1.size()!=5) { System.out.println("size fails"); return false; }
    System.out.println(t1);
    
    return true;
  }

  /**
   * This method checks mainly for the correctness of the MovieTree.contains() method. 
   * It must consider at least the following test scenarios. 
   * (1) Create a new MovieTree. Then, check that calling the contains() 
   * method on an empty MovieTree returns false. 
   * (2) Consider a MovieTree of height 3 which contains at least 5 movies. 
   * Then, try to call contains() method to search for the movie 
   * having a match at the root of the tree. 
   * (3) Then, search for a movie at the right and left subtrees at different 
   * levels considering successful and unsuccessful search operations.
   * Make sure that the contains() method returns the expected output for every method call.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testContains() {
    MovieTree t5 = new MovieTree();
    if (t5.contains(1950, 4, "Whatever")) 
      { System.out.println("contains fails"); return false; }
    
    t5.addMovie(new Movie(1950, 4, "Whatever"));
    t5.addMovie(new Movie(1930, 4, "Metropolotis"));
    t5.addMovie(new Movie(1965, 4, "Adventure"));
    t5.addMovie(new Movie(1940, 1, "Game"));
    t5.addMovie(new Movie(1970, 4, "Shark"));
    if (!t5.contains(1950, 4, "Whatever")) 
      { System.out.println("contains fails"); return false; }
    
    if (!t5.contains(1930, 4, "Metropolotis")) 
      { System.out.println("contains fails"); return false; }
    
    if (!t5.contains(1930, 4, "Metropolotis")) 
      { System.out.println("contains fails"); return false; }
    
    return true;
  }

  /**
   * Checks for the correctness of MovieTree.height() method. 
   * This test must consider several scenarios such as, 
   * (1) ensures that the height of an empty movie tree is zero. 
   * (2) ensures that the height of a tree which consists of only one node is 1. 
   * (3) ensures that the height of a MovieTree with 
   * the following structure for instance, is 4. 
   *       (*)
   *     /    \
   *  (*)      (*)
   *    \     /  \
   *    (*) (*)  (*)
   *             /
   *           (*)
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testHeight() {
    MovieTree t2 = new MovieTree();
    if (t2.height()!=0) { System.out.println("height fails"); return false; }
    t2.addMovie(new Movie(1950, 4, "Whatever"));
    if (t2.height()!=1) { System.out.println("height fails"); return false; }
    t2.addMovie(new Movie(1930, 4, "Metropolotis"));
    t2.addMovie(new Movie(1965, 4, "Adventure"));
    t2.addMovie(new Movie(1940, 1, "Game"));
    t2.addMovie(new Movie(1970, 4, "Shark"));
    t2.addMovie(new Movie(2000, 5, "Vampire"));
    t2.addMovie(new Movie(1900, 4, "TheGreatCity"));
//    System.out.println(t2);
    if (t2.height()!=4) { System.out.println("height fails"); return false; }
    return true;
  }

  /**(-1.0): Tests whether MovieTreeTester.testGetBestMovie() returns false 
  on a broken implementation of MovieTree.getBestMovie() which may return 
  an incorrect result, or throw an unexpected Exception.
  Encountered trouble running MovieTreeTester.testGetBestMovie 
  with arguments: []. Encountered NoSuchElementException in file MovieTree.java 
  on line 209.Exception Message: Empty movie collection!
  Note: submission/MovieTree.java uses unchecked or unsafe operations.
  Note: Recompile with -Xlint:unchecked for details.
  */
  
  /**
   * Checks for the correctness of MovieTree.getBestMovie() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetBestMovie(){
    MovieTree t3 = new MovieTree();
    
    try {
      if (t3.getBestMovie()!=null) {
        System.out.println("GetBestMovie fails"); return false;
      }
    }
    catch (NullPointerException e){
      // exception with no error message
      if(e.getMessage() == null || e.getMessage().length() == 0) { 
        System.out.println("Empty movie collection!"); return false;
      }
      else { 
        System.out.println(e.getMessage()); 
        return false;
      }
    }
    
    t3.addMovie(new Movie(1950, 4, "Whatever"));
    t3.addMovie(new Movie(1930, 4, "Metropolotis"));
    t3.addMovie(new Movie(1965, 4, "Adventure"));
    t3.addMovie(new Movie(1940, 1, "Game"));
    t3.addMovie(new Movie(1970, 4, "Shark"));
    t3.addMovie(new Movie(2000, 5, "Vampire"));
    
    try {
      Movie bst = t3.getBestMovie();
      if (!bst.toString()
          .equals("[(Year: 2000) (Rate: 5.0) (Name: Vampire)]")) { 
        return false;
      }
    }
    catch (NullPointerException e) {
      System.out.println(e.getMessage());
      return false;
    }    
    
    return true; 
  }

  /**
   * Checks for the correctness of MovieTree.lookup() method. 
   * This test must consider at least 3 test scenarios. 
   * (1) Ensures that the MovieTree.lookup() method throws a NoSuchElementException
   * when called on an empty tree. 
   * (2) Ensures that the MovieTree.lookup() method returns an array list which 
   * contains all the movies satisfying the search criteria of year and rating, 
   * when called on a non empty movie tree with one match, 
   * and two matches and more. Vary your search criteria such that the lookup() 
   * method must check in left and right subtrees. 
   * (3) Ensures that the MovieTree.lookup() method throws a NoSuchElementException 
   * when called on a non-empty movie tree with no search results found.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testLookup() {
    MovieTree t4 = new MovieTree();
    ArrayList<Movie> listX = null;
    try { listX=t4.lookup(1950, 3); }
    // If no match found throw a NoSuchElementException 
    catch (NoSuchElementException e) { System.out.println(e.getMessage());}
    
    t4.addMovie(new Movie(1950, 1, "Whatever"));
    t4.addMovie(new Movie(1930, 2, "Metropolotis"));
    t4.addMovie(new Movie(1950, 3, "Adventure"));
    t4.addMovie(new Movie(1940, 1, "Game"));
    t4.addMovie(new Movie(1950, 2, "Shark"));
    t4.addMovie(new Movie(1950, 4, "Vampire"));
    
    try { listX=t4.lookup(1950, 3); }
    // If no match found throw a NoSuchElementException 
    catch (NoSuchElementException e) { System.out.println(e.getMessage());}
//    System.out.println(listX);
    if (!listX.toString().equals("[[(Year: 1950) (Rate: 3.0) (Name: Adventure)], "
        + "[(Year: 1950) (Rate: 4.0) (Name: Vampire)]]"))
      return false;
    return true;
  }
  
  public static void demo() {
    MovieTree bst = new MovieTree();
    System.out.println("Size: " + bst.size() + " Height: " + bst.height() + "\nCatalog:");
    System.out.println(bst);
    bst.addMovie(new Movie(2018, 6.5, "Airplanes"));
    bst.addMovie(new Movie(1988, 9.5, "Best"));
    System.out.println("==============================================================");
    System.out.println("Size: " + bst.size() + " Height: " + bst.height() + "\nCatalog:");
    System.out.println(bst);
    bst.addMovie(new Movie(2018, 8.5, "Cats"));
    bst.addMovie(new Movie(2018, 6.0, "Yes"));
    bst.addMovie(new Movie(2017, 5.5, "Dogs"));
    bst.addMovie(new Movie(2018, 7.5, "Earth"));
    bst.addMovie(new Movie(2018, 6.0, "Flights"));
    bst.addMovie(new Movie(2015, 8.5, "Grand Parents"));
    System.out.println("==============================================================");
    System.out.println("Size: " + bst.size() + " Height: " + bst.height() + "\nCatalog:");
    System.out.println(bst);
    try {
      System.out.println("This catalog contains (2018, 7.5, Earth): " + bst.contains(2018, 7.5, "Earth"));
      System.out.println("This catalog contains (2016, 8.4, Flowers): " + bst.contains(2018, 7.5, "Flowers"));
      System.out.println();
      System.out.println("Best movie: " + bst.getBestMovie());
      System.out.println();
      System.out.println("Lookup query: search for the movies of 2018 rated 6.5 and higher");
      System.out.println(bst.lookup(2018, 6.5));
      System.out.println();
      System.out.println("Lookup query: search for the movies of 2018 with rated 8.0 and higher");
      System.out.println(bst.lookup(2018, 8.0));
      System.out.println();
      System.out.println("Lookup query: search for the movies of 2015 with rated 9.0 and higher");
      System.out.println(bst.lookup(2015, 9.0));
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }
  
  /**
   * Calls the test methods
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
//    demo();
    System.out.println("testAddMovieToStringSize(): " + testAddMovieToStringSize());
    System.out.println("testHeight(): " + testHeight());
    
    try {
      System.out.println("testGetBestMovie(): " + testGetBestMovie());
    } 
    catch (Exception e){
      System.out.println(e.getMessage());
    }
    
    System.out.println("testLookup(): " + testLookup());
    System.out.println("testContains(): " + testContains());
  }

}