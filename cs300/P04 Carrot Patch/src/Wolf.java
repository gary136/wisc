//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define how Wolf acts
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
 * Implement the details of Wolf
 */

public class Wolf extends Animal{
  
  // 7.1 Define the Wolf ’s data fields
  // path to the wolf image file
  private static final String WOLF = "images" + File.separator + "wolf.png";
  private static int scanRange = 120; // scanning range to look for a rabbit
  // in the neighborhood
  private static int nextID = 1; // identifier of the next Wolf to be created
  private static final String TYPE = "W"; // A String that represents the Wolf type
  private final int ID; // positive number that represents the order of this Wolf
  private int rabbitEatenCount; // Number of rabbits that the current Wolf has eaten so far
  
  // 7.2 Implement the constructor and getter methods of the Wolf class
  /**
   * Creates a new Wolf object at a random position of the display window
   *
   * @param processing CarrotPatch object which represents the display window
   */
  public Wolf() {
 // Set rabbit drawing parameters
    super(WOLF);
    // Set rabbit identification fields
    ID = nextID;
    this.label = TYPE + ID; // String that identifies the current rabbit
    nextID++;
  }
  public int getRabbitEatenCount(){return rabbitEatenCount;} // gets rabbitEatenCount instance field
  public static int getScanRange(){return scanRange;} // gets the scanRange of a Wolf

  /**
  * Moves to the position of the specified rabbit passed as input, and eats it.
  * The eaten rabbit will be removed from the patch and he number of eaten
  * rabbits by this wolf is incremented by one.
  * @param rabbit rabbit to eat by this wolf
  */
  public void eatRabbit(Rabbit rabbit) {
    // if the mouse is over the current Wolf, release it so the Wolf can move
    // ahead to the position of rabbit and eat it.
//    System.out.println("check if "+ this + " can eat rabbit");
    if (isMouseOver()) {
      this.mouseReleased();
      // TODO
      // 1. set the position of the current Wolf to the position of the rabbit
      // 2. remove the rabbit from the patch
      // 3. increment the number of eaten rabbits by one
//      System.out.println(this + " eats rabbit");
      
      this.setX(rabbit.getX());
      this.setY(rabbit.getY());
      processing.objects.remove(rabbit);
      
      this.rabbitEatenCount++;
    }
  }
  
  /**
  * Defines the action of this wolf in the carrot patch. This wolf lookup for
  * rabbits in its neighborhood (Wolf.scanRange) and eats the first found rabbit
  * only. This method also displays the number of rabbit eaten by this wolf if any.
  */
  @Override
  public void action() {
  // TODO
  //Traverse processing.objects arraylist, search for the first rabbit which
  // is close to this wolf with respect to Wolf.scanRange, and eats it.
  // If no rabbit is found in the neighborhood, nothing will be done.
    for (int i = 0; i < processing.objects.size(); i++) {
      if (processing.objects.get(i) instanceof Rabbit 
          && this.isClose((Rabbit) processing.objects.get(i), Wolf.scanRange)) {
//        System.out.println("eat " + processing.objects.get(i));
        
        this.eatRabbit((Rabbit) processing.objects.get(i));
      }
    }
  if (rabbitEatenCount > 0)
  displayrabbitEatenCount(); // display rabbitEatenCount
  }
  
  /**
  * Displays the number of eaten rabbits if any on the top of the Wolf image
  */
  public void displayrabbitEatenCount() {
  processing.fill(0); // specify font color: black
  // display rabbitEatenCount on the top of the Wolf’s image
  processing.text(rabbitEatenCount, this.getX(), this.getY() - this.image.height / 2 - 6);
  }
}
