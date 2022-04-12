//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define Palindrome
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
 * Implement the details of Palindrome
 */

public class Palindrome {
  
  /**
   * keep adding alphabet after given String 
   * alphabet changes by a given "step"
   * terminates when alphabet < 'A'
   * 
   * @param toIncrmnt - the String to increment
   * @param step - controls how alphabet changes
   * @return the String after the increment
   */
  private static String minusA(String toIncrmnt, int step) {
    char curr = toIncrmnt.charAt(toIncrmnt.length()-1); // last element of current string
    char next = (char) (curr-step); // the alphabet after moving "step" backward
 // System.out.print("- nxt=" + next + " | ");
    if (next<'A') { // base, stopping the recursion
   // System.out.println("toIncrmnt = " + toIncrmnt);
      if (curr=='A') return toIncrmnt; 
      return toIncrmnt + " " + curr; // if not A, copy the last element
      }
    return minusA(toIncrmnt + " " + next, step);
  }
  
  /**
   * keep adding alphabet after given String 
   * alphabet changes by a given "step"
   * terminates when alphabet > the first element of given String
   * 
   * @param toIncrmnt - the String to increment
   * @param step - controls how alphabet changes
   * @return the String after the increment
   */
  private static String plusSelf(String toIncrmnt, int step) {
    char curr = toIncrmnt.charAt(toIncrmnt.length()-1); // last element of current string
    char next = (char) (curr+step); // the alphabet after moving "step" forward
 // System.out.print("+ nxt=" + next + " | ");
    if (next>toIncrmnt.charAt(0)) { // base, stopping the recursion
   // System.out.println("toIncrmnt = " + toIncrmnt);
      return toIncrmnt;
      }
    return plusSelf(toIncrmnt + " " + next, step);
  }
  
  /**
   * keep adding alphabet after given String 
   * alphabet changes by a given "step"
   * terminates when alphabet > 'Z'
   * 
   * @param toIncrmnt - the String to increment
   * @param step - controls how alphabet changes
   * @return the String after the increment
   */
  private static String plusZ(String toIncrmnt, int step) {
    char curr = toIncrmnt.charAt(toIncrmnt.length()-1); // last element of current string
    char next = (char) (curr+step); // the alphabet after moving "step" forward
 // System.out.print("+ nxt=" + next + " | ");
    if (next>'Z') { // base, stopping the recursion
   // System.out.println("toIncrmnt = " + toIncrmnt);
      if (curr=='Z') return toIncrmnt;
      return toIncrmnt + " " + curr; // if not Z, copy the last element
      }
    return plusZ(toIncrmnt + " " + next, step);
  }
  
  /**
   * keep adding alphabet after given String 
   * alphabet changes by a given "step"
   * terminates when alphabet < the first element of given String
   * 
   * @param toIncrmnt - the String to increment
   * @param step - controls how alphabet changes
   * @return the String after the increment
   */
  private static String minusSelf(String toIncrmnt, int step) {
    char curr = toIncrmnt.charAt(toIncrmnt.length()-1); // last element of current string
    char next = (char) (curr-step); // the alphabet after moving "step" backward
 // System.out.print("- nxt=" + next + " | ");
    if (next<toIncrmnt.charAt(0)) { // base, stopping the recursion
   // System.out.println("toIncrmnt = " + toIncrmnt);
      return toIncrmnt;
      }
    return minusSelf(toIncrmnt + " " + next, step);
  }
  
  /**
   * Recursively create a simple alphabet pattern, 
   * starting at the provided character, 
   * moving backward to the beginning of the alphabet, 
   * and then forward again to the provided letter, 
   * separating each letter with a space
   * 
   * @param start - the provided character
   * @return the String after increment
   */
  public static String mirrorA(char start) throws IllegalArgumentException {
    // only valid for ​capital letter input​
    if (start<'A' || start >'Z') 
      throw new IllegalArgumentException("invalid argument");
    // move backward then forward
    return plusSelf(minusA(Character.toString(start), 1), 1);
  }
  
  /**
   * Recursively create an alphabet pattern, 
   * starting at the provided character, 
   * and moving back and forth to the beginning of the alphabet 
   * by steps of size ​step​
   * 
   * @param start - the provided character
   * @return the String after increment
   */
  public static String mirrorA(char start, int step) throws IllegalArgumentException {
    // step is ​strictly positive​ & only valid for capital letter input 
    if (step <= 0 || start<'A' || start >'Z') 
                                              
      throw new IllegalArgumentException("invalid argument");
    // move backward then forward
    return plusSelf(minusA(Character.toString(start), step), step);
  }

  
  /**
   * Recursively create a simple alphabet pattern, 
   * starting the provided character, 
   * and moving forward to the end of the alphabet, 
   * and then backward again to the provided letter, 
   * separating each letter with a space
   * 
   * @param start - the provided character
   * @return the String after increment
   */
  public static String mirrorZ(char start) throws IllegalArgumentException {
    // only valid for ​capital letter input​
    if (start<'A' || start >'Z')
      throw new IllegalArgumentException("invalid argument");
    // move forward then backward
    return minusSelf(plusZ(Character.toString(start), 1), 1);
  }
  
  /**
   * Recursively create an alphabet pattern, 
   * starting at the provided character, 
   * and moving forward and back to the end of the alphabet 
   * by steps of size ​step​.
   * 
   * @param start - the provided character
   * @return the String after increment
   */
  public static String mirrorZ(char start, int step) throws IllegalArgumentException {
    // step is ​strictly positive​ & only valid for capital letter input 
    if (step <= 0 || start<'A' || start >'Z')
      throw new IllegalArgumentException("invalid argument");
    // move forward then backward
    return minusSelf(plusZ(Character.toString(start), step), step);
  }
}

