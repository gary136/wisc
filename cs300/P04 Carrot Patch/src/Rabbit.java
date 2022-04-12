//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define how Rabbit acts
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

/**
 * Implement the details of Rabbit
 */

public class Rabbit extends Animal{
  // 6.1 Define Rabbit’s data fields, constructor, and getter methods
  private static final String RABBIT = "images"+ File.separator + "rabbit.png";
  private static final String TYPE = "R"; // A String that represents the rabbit type
  private static int hopStep = 70; // one hop step
  private static int scanRange = 175; // scan range to watch out for threats
  private static int nextID = 1; // class variable that represents the identifier
                              // of the next rabbit to be created
  private final int ID; // positive number that represents the order of this rabbit
  /**
   * Creates a new rabbit object located at a random position of the display window
   */
  public Rabbit() {
    // Set rabbit drawing parameters
    super(RABBIT);
    // Set rabbit identification fields
    ID = nextID;
    this.label = TYPE + ID; // String that identifies the current rabbit
    nextID++;
  }

  //getter of Rabbit.scanRange static field
  public static int getScanRange() {return scanRange;}
   
  //getter of Rabbit.hopStep static field
  public static int getHopStep() {return hopStep;}
  
  // 10 Implement the Rabbit specific behaviors in the CarrotPatch

  /**
  * Gets the first carrot in the patch. If the carrot is in the Rabbit
  * hopStep range, the rabbit eats it. It sets its position to the (x,y)
  * position of the carrot and the carrot will be removed from the Carrot Patch.
  * Otherwise, the rabbit moves one hopStep towards that carrot. If no carrot
  * found (meaning Carrots.getFirstCarrot() returns false),
  * the rabbit does nothing.
  */
  public void hopTowardsCarrot() {
    // get the first carrot
    int[] carrot = Carrots.getFirstCarrot();
    if (carrot != null) {
    // TODO complete the implementation of this method
      if (isClose(carrot[1], carrot[2], Rabbit.getHopStep())) {
        // eats
//        System.out.println(this+" eats carrot");
        this.setX(carrot[1]);
        this.setY(carrot[2]);
        Carrots.remove(carrot[0]);
        this.mouseReleased();
      }
      else {
        // hops
//        System.out.println(this+" hops toward carrot");
        int dx = carrot[1] - this.getX();
        int dy = carrot[2] - this.getY();
        int totalDistance = (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double newX = this.getX() + Rabbit.hopStep * dx / totalDistance;
        double newY = this.getY() + Rabbit.hopStep * dy / totalDistance;
        this.setX((int) newX);
        this.setY((int) newY);
        this.mouseReleased();
      }
    }
  }
  
  // Override partially Animal methods
 /**
  * checks whether the mouse is over this animal
  * @return true if the mouse is over this animal, false otherwise.
  */
  @Override
  public void mousePressed() {
  // TODO
  // call the mousePressed defined in the Animal super class
  // call hopTowardsCarrot() method
    super.mousePressed();
    this.hopTowardsCarrot();
  }
  
    /**
    * This method watches out for wolves. Checks if there is a wolf
    * in the Rabbit.scanRange of this Rabbit.
    *
    * @return true if the current rabbit is close to at least one wolf
    */
    public boolean watchOutForWolf() {
    // TODO complete the implementation of this method
    // Traverse the processing.objects arraylist checking whether there is a wolf which is 
    // close by Rabbit.scanRange of this rabbit.
    for (int i = 0; i < processing.objects.size(); i++) {
      if (processing.objects.get(i) instanceof Wolf 
          && this.isClose((Animal) processing.objects.get(i), Rabbit.scanRange)) {
//        System.out.println("find " + processing.objects.get(i));
        return true;
      }
    }
    return false;
    }
    
    
    
    /**
    * Watches out for a wolf and display a Warning message "WOLF!"
    * if there is any wolf in its neighborhood.
    */
    @Override
    public void action() {
      if (watchOutForWolf()) {
      // this.setScaredImage();
      processing.fill(0); // specify font color: black
      processing.text("WOLF!", this.getX(), this.getY() - this.image.height / 2 - 6);
    }
  }
}
