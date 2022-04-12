//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define tester of Palindrome
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
 * Implement the details of PalindromeTester
 */

public class PalindromeTester {
  
  /**
   * check robustness of testMirrorA
   * 
   * @return the incremented String
   */
  public static boolean testMirrorA() {
    // scenario 1 valid input
    try {
    char testIn = 'E';
    String testOut = Palindrome.mirrorA(testIn);
    System.out.println(testIn + " generates " + testOut);
    if (!testOut.equals("E D C B A B C D E")) return false;
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    
    // scenario 2 invalid input
    try {
    char testIn = 'e';
    String testOut = Palindrome.mirrorA(testIn); // should trigger exceptions
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    return true;
  }
  
  /**
   * check robustness of testMirrorAStep
   * 
   * @return the incremented String
   */
  public static boolean testMirrorAStep() {
    
    // scenario 1 valid input
    try {
      char testIn = 'E';
      String testOut = Palindrome.mirrorA(testIn, 2);
      System.out.println(testIn + " generates " + testOut);
      if (!testOut.equals("E C A C E")) return false;
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    
    // scenario 2 invalid input
    try {
      char testIn = 'e';
      String testOut = Palindrome.mirrorA(testIn, 2); // should trigger exceptions
      }
      catch(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }
    
    // scenario 3 valid input
    try {
      char testIn = 'E';
      String testOut = Palindrome.mirrorA(testIn, 3);
      System.out.println(testIn + " generates " + testOut);
      if (!testOut.equals("E B B E")) return false;
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    
    // scenario 4 invalid input
    try {
      char testIn = 'E';
      String testOut = Palindrome.mirrorA(testIn, -1); // should trigger exceptions
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    
    return true;
  }
  
  /**
   * check robustness of testMirrorZ
   * 
   * @return the incremented String
   */
  public static boolean testMirrorZ() {
    
    // scenario 1 valid input
    try {
    char testIn = 'V';
    String testOut = Palindrome.mirrorZ(testIn);
    System.out.println(testIn + " generates " + testOut);
    if (!testOut.equals("V W X Y Z Y X W V")) return false;
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    
    // scenario 2 invalid input
    try {
    char testIn = 'v';
    String testOut = Palindrome.mirrorZ(testIn); // should trigger exceptions
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    return true;
  }
  
  /**
   * check robustness of testMirrorZStep
   * 
   * @return the incremented String
   */
  public static boolean testMirrorZStep() {
    
    // scenario 1 valid input
    try {
    char testIn = 'V';
    String testOut = Palindrome.mirrorZ(testIn, 2);
    System.out.println(testIn + " generates " + testOut);
    if (!testOut.equals("V X Z X V")) return false;
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    
    // scenario 2 invalid input
    try {
      char testIn = 'v';
      String testOut = Palindrome.mirrorZ(testIn, 2); // should trigger exceptions
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    
    // scenario 3 valid input
    try {
      char testIn = 'V';
      String testOut = Palindrome.mirrorZ(testIn, 3);
      System.out.println(testIn + " generates " + testOut);
      if (!testOut.equals("V Y Y V")) return false;
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    
    // scenario 4 invalid input
    try {
      char testIn = 'V';
      String testOut = Palindrome.mirrorZ(testIn, -1); // should trigger exceptions
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    return true;
  }
  
  /**
   * call 4 testers
   * 
   * @return whether 4 testers return true
   */
  
  public static boolean runAllTest() {
    return testMirrorA() && testMirrorAStep() && testMirrorZ() && testMirrorZStep();
  }
  
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println(runAllTest());
  }

}
