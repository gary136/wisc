/////////////////////////////// EXAM FILE HEADER ///////////////////////////////
//Full Name: Lee Hung Ting
//Campus ID: 9083057233
//WiscEmail: hlee864@wisc.edu
////////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class implements methods to manage covid-19 AstraZeneca vaccine registration and then giving
 * one doze to the registrated recipients. Recipients are stored in an oversize array (String[]
 * recipients, int size). A new recipient will be added to the front (at index 0) of this array.
 * People at the back/end of the recipients array will get vaccines first. Vaccinated people will be
 * moved to an ArrayList. Only the first doze of the vaccine will be given to each recipient. NOTE
 * that the recipients oversize array is NOT a bag. The order of elements must be maintained after
 * adding or removing operations.
 * 
 * TODO: Complete the implementation of the two methods below with respect to the details provided
 * in their javadoc style method headers. 
 * 1. init() 
 * 2. addRecipientToFront() 
 * 3. giveVaccineDoseToLast()
 * 
 * Note that when creating new exception objects, including descriptive messages within these
 * objects is optional.
 * You do NOT need to follow the couse style guide or commenting requirements for this exam.
 *
 */
// MAKE SURE TO SAVE your source file before uploading it to gradescope.
public class AstraZenecaVaccine {

  private static ArrayList<String> vaccinated; // list of people who have taken the first dose of
  // the vaccine
  private static int dosesCount; // number of covid-19 vaccine doses available

  /**
   * Creates an empty list of vaccinated people, and sets the initial number of covid-19 vaccine
   * dozes available to 20
   */
  public static void init() {
    // TODO Complete the implementation of this method
    vaccinated = new ArrayList<String>();
    dosesCount = 20;
  }
  
  
  //MAKE SURE TO SAVE your source file before uploading it to gradescope.
  /**
   * Registers a new recipient by adding it to the beginning (index 0) of the oversize recipients
   * array. Recall that recipients array is NOT a bag. The order of elements stored in the recipients 
   * array must be maintained after adding newRecipient to the array. This means that a shift 
   * operation is needed. If there is no room in the recipients array to add the new recipient, 
   * the message error "Warning! Full Array!" will be printed without making any change to the 
   * contents of the array. To be eligible to take the covid-19 AstraZeneca vaccine and be added 
   * to the list of recipients, the recipient must not be older than 55.
   * 
   * @param recipients   array which stores the recipients registered to take Covid19 pfizer vaccine
   * @param size         total number of registred recipients
   * @param newRecipient name of the recipient to register
   * @param age          age of the new recipient to register
   * @return the size of recipients array after trying to add the new recipient name
   * @throws an IllegalArgumentException with or without error message if newRecipient is not
   *            eligible to take this vaccine (if their age is greater than (>) 55)
   */
  public static int addRecipientToFront(String[] recipients, int size, String newRecipient,
      int age) throws IllegalArgumentException {
    // TODO Complete the implementation of this method
    if (size==recipients.length) {
      System.out.println("Warning! Full Array!");
      return size;
    }
    // Error checking
    if (age > 55) {
       throw new IllegalArgumentException("newRecipient is not eligible");
    }
    for (int i=size; i>0; i--) {
      recipients[i] = recipients[i-1];
    }
    recipients[0] = newRecipient;
    
    return size+1; // CHANGE this (added to avoid compile errors)
  }

  //MAKE SURE TO SAVE your source file before uploading it to gradescope.
  /**
   * Gives one vaccine dose to the recipient stored at the end of the recipients array and moves it
   * to the vaccinated arraylist (remove the last element from the recipients array and add it to
   * the vaccinated arraylist). This operation fails (no dose will be given and the method returns
   * without making any changes to recipients array and the vaccinated arraylist) if (1) There are
   * zero doses left when this method is called. In that case, an IllegalStateException will be
   * thrown. (2) The recipients array is empty. In that case, the following error message will be
   * displayed "Error! No registered recipients in the list."
   * 
   * Make sure to update the dosesCount after giving the dose to the recipient.
   * 
   * @param recipients oversize array which stores the names of the vaccine's recipients list
   * @param size       number of elements stored in the recipients array
   * @return the size of the recipients array after trying to give the vaccine to the recipient
   *         stored at the end of the recipients array.
   * @throws IllegalStateException with or without an error message if dosesCount is zero.
   */
  public static int giveVaccineDoseToLast(String[] recipients, int size) throws IllegalArgumentException {
    // TODO Complete the implementation of this method
    
    // Error checking
    if (dosesCount==0) {
      throw new IllegalArgumentException("dosesCount=0");
    }
    if (recipients.length==0) {
      throw new IllegalArgumentException("Error! No registered recipients in the list.");
    }
    
    String recipient = recipients[size-1];
    recipients[size-1] = null;
    vaccinated.add(recipient);
    dosesCount--;
    
    return size-1; // CHANGE this (added to avoid compile errors)
  }

  /**
   * Checks the correctness of addRecipientToFront() when there is room to register new recipients
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddRecipient() {

    // Create an empty oversize recipients array
    String[] recipients = new String[5];
    int size = 0;
    try {
      // Try to add two recipients younger than 55 and a senior
      size = addRecipientToFront(recipients, size, "Bob", 25);
      // for (int i=0;i<recipients.length;i++) {System.out.print(recipients[i] + " ");} 
      // System.out.print("\n");
      size = addRecipientToFront(recipients, size, "George", 30);
      // for (int i=0;i<recipients.length;i++) {System.out.print(recipients[i] + " ");} 
      // System.out.print("\n");
      size = addRecipientToFront(recipients, size, "Emma", 75);
      // for (int i=0;i<recipients.length;i++) {System.out.print(recipients[i] + " ");} 
      // System.out.print("\n");
      // IllegalArgumentException expected // to be thrown
      System.out
          .println("Problem detected: No exception was thrown while trying to add (Emma, 75)");
      return false;

    } catch (IllegalArgumentException e1) {
      String[] expectedRecipients = new String[] {"George", "Bob", null, null, null};
      int expectedSize = 2;
      if (size != expectedSize) {
        System.out.println("Problem detected: The returned size was incorrect.");
        return false;
      }
      if (!Arrays.deepEquals(recipients, expectedRecipients)) {
        System.out.println(Arrays.toString(recipients) + " "
            + "Problem detected: The contents of the recipients array was not as expected.");
        return false;
      }
    } catch (Exception e2) {
      System.out.println(
          "Problem detected: An exception other than IlegalArgumentException has been thrown.");
      e2.printStackTrace();
      return false;
    }

    return true; // test passed without errors
  }

  /**
   * Checks the correctness of addRecipientToFront() when there is NO room to register new
   * recipients
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddRecipientNoRoom() {

    // Create a full oversize recipients array
    String[] recipients = new String[] {"Michelle", "Sulong"};
    int size = 2;
    try {
      // Try to add a recipient to a full recipients array. An IllegalStateException is expected to
      // be thrown without making any change to the contents of the array.
      size = addRecipientToFront(recipients, size, "Ryan", 40);
      String[] expectedRecipients = new String[] {"Michelle", "Sulong"};
      int expectedSize = 2;
      if (size != expectedSize) {
        System.out.println(
            "Problem detected: No changes must be made to the size of the recipients array.");
        return false;
      }
      if (!Arrays.deepEquals(recipients, expectedRecipients)) {
        System.out.println("Problem detected: The contents of the recipients array has changed!");
        return false;
      }
    } catch (Exception e) {// non-expected behavior
      System.out.println("Problem detected: An exception has been thrown.");
      e.printStackTrace();
      return false;
    }

    return true; // test passed without errors
  }


  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println("testAddRecipient:" + testAddRecipient());
    System.out.println("testAddRecipientNoRoom:" + testAddRecipientNoRoom());
  }



}