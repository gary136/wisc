//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define the implementation details of Person class
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
 * Implement the details how a Person class should work
 */
public class Person {
  private String name; // a String representing the Person’s name
  private boolean isWaiting; // a boolean which has the value true if and only if the 
                             // Person is ​NOT ​currently in a Room
  
  /**
   * Constructor
   * 
   * @param name - a person's name
   */
  public Person(String name) {
    this.name = name;
    // Person objects should be assumed to NOT be in a Room at the time of creation
    // -> isWaiting = true
    this.isWaiting = true;
  }
  
  /**
   * Returns the name of person
   * 
   * @return the name of person
   */
  public String getName() {
    return name;
  }
  
  /**
   * Returns the state of isWaiting
   * 
   * @return the state of isWaiting
   */
  public boolean isWaiting (){
    return this.isWaiting;
  }
  
  /**
   * changes the state of isWaiting
   * 
   */
  public void toggleWaiting() {
    // Sets isWaiting to true if it is currently false, and to false if it is currently true
    if (this.isWaiting==true) {
      this.isWaiting=false;
    }
    else {
      this.isWaiting=true;
    }
  }
  
  /**
   * Compare Person with another Object
   * 
   * @return whether Person = Object
   */
  public boolean equals(Object o) {
    if (o instanceof Person) { // test whether o is an instance of Person
      return this.name.equals(((Person) o).name); 
      // compare name instead of reference, 
      // therefore different object with same name would return true
    }
    return false;
  }
}
