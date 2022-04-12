//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: test the robustness of carrot patch
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

import processing.core.PApplet;

/**
 * Implement the details of carrot patch
 */

public class CarrotPatchTester extends CarrotPatch {

  private static CarrotPatch processing; // PApplet object that represents the display window of
                                         // this program

  /**
   * This method checks whether isClose(Animal, int) called by a Rabbit returns true if a Wolf is
   * within the rabbit's scanRange area and false if called with another Wolf as input parameter
   * located outside its scanRange area
   * 
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean test1isCloseMethod() {
    Carrots.settings(processing);
    Animal.setProcessing(processing);
    Button.setProcessing(processing);

    // This is a suggested example. You can define your own test scenario for this method
    // Create a Rabbit and two Wolves
    try {
      Rabbit r = new Rabbit();
      Wolf w1 = new Wolf();
      Wolf w2 = new Wolf();
      // Set Rabbit at position(200,200)
      r.setX(200);
      r.setY(200);
      // Set first Wolf at position(400,200)
      w1.setX(400); // Wolf is 200px away from Rabbit
      w1.setY(200);
      // Set second Wolf at position(300,200)
      w2.setX(300); // Wolf is 100px away from Rabbit
      w2.setY(200);
      if (r.isClose(w1, Rabbit.getScanRange())) { // bug! isClose() should return false here
        System.out.println(
            "Problem detected: Rabbit.isClose() returned true when it should return false.");
        return false;
      }
      if (!r.isClose(w2, Rabbit.getScanRange())) { // bug! isClose() should return true here
        System.out.println(
            "Problem detected: Rabbit.isClose() returned false when it should return true.");
        return false;
      }

      /////////////////////////////////////
      processing.objects.clear(); // clear all the content of objects list to get ready to the next
                                  // test scenario
    } catch (Exception e) {
      System.out.println("Problem detected: A non-expected exception has been thrown.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * This method checks whether isClose(Animal, int) called by a Wolf returns false when called to
   * check whether it is close to another wolf located outside the Wolf's scanRange area
   * 
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean test2isCloseMethod() {
    Carrots.settings(processing);
    Animal.setProcessing(processing);
    Button.setProcessing(processing);
    // TODO Define your test scenario here
     // Create two Wolves
    try {
      Wolf w1 = new Wolf();
      Wolf w2 = new Wolf();
      // Set first Wolf at position(100,0)
      w1.setX(100); 
      w1.setY(0);
      // Set second Wolf at position(100,40)
      w2.setX(100); // Wolf1 is 40px away from Wolf2
      w2.setY(40);
      if (!w1.isClose(w2, Wolf.getScanRange())) { // bug! isClose() should return true here
        System.out.println(
            "Problem detected: Wolf.isClose() returned false when it should return true.");
        return false;
      }

    ////////////////////////////////////////
    processing.objects.clear(); // clear all the content of objects list to get ready to the next
                                // test scenario
    } catch (Exception e) {
      System.out.println("Problem detected: A non-expected exception has been thrown.");
      e.printStackTrace();
      return false;
    }
    return true;
  }


  /**
   * This method checks whether isClose(int, int) called by a Rabbit returns false when called to
   * check whether this rabbit is close to a given (x,y) position located outside of its
   * 
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean test3isCloseMethod() {
    Carrots.settings(processing);
    Animal.setProcessing(processing);
    Button.setProcessing(processing);
    // TODO Define your test scenario here
    // Create a Rabbit
    try {
      Rabbit r = new Rabbit();
      // Set Rabbit at position(200,200)
      r.setX(200);
      r.setY(200);
      if (r.isClose(400, 200, Rabbit.getScanRange())) { // bug! isClose() should return false here
        System.out.println(
            "Problem detected: Rabbit.isClose() returned true when it should return false.");
        return false;
      }
      
      /////////////////////////////////////
      processing.objects.clear(); // clear all the content of objects list to get ready to the next
                                  // test scenario
    } catch (Exception e) {
      System.out.println("Problem detected: A non-expected exception has been thrown.");
      e.printStackTrace();
      return false;
    }

    ////////////////////////////////////////
    processing.objects.clear(); // clear all the content of objects list to get ready to the next
                                // test scenario
    return true;
  }

  /**
   * This method checks whether the Rabbit detects a Wolf present at its proximity
   * 
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean test1RabbitWatchForWolfMethod() {
    Carrots.settings(processing);
    Animal.setProcessing(processing);
    Button.setProcessing(processing);
    // test scenario
    try {
      Rabbit r = new Rabbit();
      Wolf w = new Wolf();
      r.setX(200);
      r.setY(200);
      w.setX(300); // Wolf is 100px away from Rabbit
      w.setY(200);
      processing.objects.add(r);
      processing.objects.add(w);

      if (!r.watchOutForWolf()) {
        System.out.println(
            "Problem detected: Rabbit's did not find Wolf within proximity, when it should have.");
        return false;
      }
      processing.objects.clear(); // clear all the content of objects added in this scenario
    } catch (Exception e) {
      System.out.println("Problem detected: A non-expected exception has been thrown.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * This method checks whether your scanForThreat() method returns false if no Wolf is present
   * within a specific range distance from it
   * 
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean test2RabbitWatchForWolfMethod() {
    Carrots.settings(processing);
    Animal.setProcessing(processing);
    Button.setProcessing(processing);
    // test scenario
    try {
      Rabbit r = new Rabbit();
      Wolf w = new Wolf();
      r.setX(200);
      r.setY(200);
      w.setX(400); // Wolf is 200px away from Rabbit
      w.setY(200);
      processing.objects.add(r);
      processing.objects.add(w);

      if (r.watchOutForWolf()) {
        System.out.println(
            "Problem detected: Rabbit's found Wolf within proximity, when there wasn't one.");
        return false;
      }
      processing.objects.clear(); // clear all the content of objects added in this scenario
    } catch (Exception e) {
      System.out.println("Problem detected: A non-expected exception has been thrown.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * This method checks whether the Wolf hops on the Rabbit provided to the eatRabbit() method as
   * input argument. (1) The Wolf should take the position of the Rabbit. (2) The unfortunate Rabbit
   * should be removed from the CarrotPatch objects. (3) The eatenRabbitCount should be incremented.
   * 
   * @return true when test verifies correct functionality, and false otherwise.
   */
  public static boolean testWolfEatRabbitMethod() {
    Carrots.settings(processing);
    Animal.setProcessing(processing);
    Button.setProcessing(processing);
    // Define the test Scenario (This is an example. You may develop different scenarios)
    // Create one Rabbit and one Wolf
    try {
      Rabbit r = new Rabbit();
      Wolf w = new Wolf();
      // Set the Rabbit at position(250,250)
      r.setX(250);
      r.setY(250);
      // Set the Wolf at position(300,300) Wolf is 70.71px away from Rabbit d1
      w.setX(300);
      w.setY(300);
      // add the Wolf and the Rabbit to the CarrotPatch (i.e. to objects)
      processing.objects.add(r);
      processing.objects.add(w);
      // manually sets mouse at the wolf's position
      processing.mouseX = 300;
      processing.mouseY = 300;
//      System.out.println(w.getX() + " " + w.getY());
      w.eatRabbit(r); // Wolf eats the rabbit r
//      System.out.println(w.getX() + " " + w.getY());
      if (w.getX() != r.getX() && w.getY() != r.getY()) {
        // Wolf should move to the position of the Rabbit
        System.out.println("Problem detected: Wolf did not move correctly when eating a rabbit.");
        return false;
      }
      if (processing.objects.contains(r)) {
        // Rabbit should be removed from the patch
        System.out.println("Problem detected: Rabbit was not removed after a wolf eats it.");
        return false;
      }
      if (w.getRabbitEatenCount() != 1) {
        // RabbitEatenCount should be incremented. It was 0
        System.out.println("RabbitEatenCount should be incremented after the Wolf eated a Rabbit.");
        return false;
      }

      processing.objects.clear(); // clear all the content of objects added in this scenario

    } catch (Exception e) {
      System.out.println("Problem detected: A non-expected exception has been thrown.");
      e.printStackTrace();
      return false;
    }
    return true;
  }
  /**
   * This is a callback method automatically called only one time when the PApplet application
   * starts as a result of calling PApplet.main("PAppletClassName"); Defines the initial environment
   * properties of this class/program As setup() is run only one time when this program starts, all
   * your test methods should be called in this method
   */
  @Override
  public void setup() {
    super.setup(); // calls the setup() method defined
    processing = this; // set the patch to the current instance of Jungle
    
    // TODO Call your test methods here
    
    System.out.println("test1isCloseMethod(): " + test1isCloseMethod());
    System.out.println("test2isCloseMethod(): " + test2isCloseMethod());
    System.out.println("test3isCloseMethod(): " + test3isCloseMethod());
    System.out.println("test1RabbitWatchForWolfMethod(): " + test1RabbitWatchForWolfMethod());
    System.out.println("test2RabbitWatchForWolfMethod(): " + test2RabbitWatchForWolfMethod());
    System.out.println("testWolfEatRabbitMethod(): " + testWolfEatRabbitMethod());
    
    // close PApplet display window (No need for the graphic mode for these tests)
    processing.exit();

  }

  /**
   * runs CarrotPatchTester program as a PApplet client
   * 
   * @param args
   */
  public static void main(String[] args) {
    // Call PApplet.main(String className) to start this program as a PApplet client application
    PApplet.main("CarrotPatchTester");
  }

}