//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: Define All the visible and interactive objects including images in this application 
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
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Implement the details of carrot patch
 */
public class CarrotPatch extends PApplet{
  
  // 2.3 Defining the CarrotPatch data fields
  
  /* fields */
  
  // the background image of this application
  private PImage backgroundImage; 
  // stores interactive objects defined in this CarrotPatch graphic application
  protected ArrayList<GUIListener> objects; 
  // not initialize until in the setup() method 
  
  // 2.4 Define the CarrotPatch methods
  
  /* methods */  
   
  // contain and override the following instance methods
  // setup (called once at the beginning of our program) 
  // draw (called repeatedly while the program runs) 
  // mousePressed (called each time the mouse is pressed) 
  // keyPressed (called each time a key is pressed) 
  
  // similar fashion with P2 Memory Game
  
  // Difference : methods are instance methods (vs static methods in P2)
   
  /**
   * Defines the initial environment properties such as screen size and to load background images
   * and fonts as the program starts. It also initializes the backgroundImage, the data fields, and
   * sets the display window for the different graphic objects which will be drawn in this
   * application
   */
  @Override
  public void setup() {
    carrotPatchSettings(); // define the graphic settings of this application
    // TODO:
    // 2.5 Initialize the CarrotPatch instance fields
    // create the list of objects
    objects = new ArrayList<GUIListener>(); // an empty ArrayList
    // load the background image 
    backgroundImage = this.loadImage("images" + File.separator + "background.png");    

    // 3.4 Set processing for the Carrots and Button classes
    // Sets this CarrotPatch as display window for all the graphic Objects 
    // pass them a reference to the current CarrotPatch object (which is the reference this)
    Carrots.settings(this);
    Button.setProcessing(this);
    
    // 4 instantiate four buttons and add them to the objects ArrayList in setup
    // Adds buttons to the display window 
    PlantCarrotsButton a = new PlantCarrotsButton(43, 16);
    AddRabbitButton b = new AddRabbitButton(129, 16);
    AddWolfButton c = new AddWolfButton(215, 16);
    RestartButton d = new RestartButton(301, 16);
    
    objects.add(a);
    objects.add(b);
    objects.add(c);
    objects.add(d);
    
    // set the processing field of all animals to the current CarrotPatch object
    Animal.setProcessing(this);
  }
  
  /**
   * Sets the display window title, text allignment, image mode, rectangle mode, and activates this
   * PApplet object to listen to the mouse events and user inputs
   */
  private void carrotPatchSettings() {
    this.getSurface().setTitle("Carrot Patch"); // Displays text in the title of the display window
    this.textAlign(PApplet.CENTER, PApplet.CENTER); // Sets the current alignment for drawing text
                                                    // to CENTER
    this.imageMode(PApplet.CENTER); // Sets the location from which images are drawn to CENTER
    this.rectMode(PApplet.CORNERS); // Sets the location from which rectangles are drawn.
    // rectMode(CORNERS) interprets the first two parameters of rect() method as the location of one
    // corner, and the third and fourth parameters as the location of the opposite corner.
    // rect() method draws a rectangle to the display window
    this.focused = true; // Confirms that our Processing program is "focused," meaning that it is
                         // active and will accept mouse or keyboard input.
  }
  /**
   * Sets the size of the application display window
   */
  @Override
  public void settings() {
    size(800, 600); // sets the size of the display window to 800 x 600 pixels
  }
  
  /**
   * Callback method called in an infinite loop. It draws the Jungle Park's window display
   */
  @Override
  public void draw() {
    // TODO: 
    // draw the background image at the center of the display window
    this.image(backgroundImage, this.width / 2, this.height / 2);
    
    // 4.1 Click on buttons
    // Draw carrots
    Carrots.draw();
    // traverse the objects list and draw all the interactive objects
    for (int i = 0; i < objects.size(); i++) {
      if (objects.get(i) != null) {
        objects.get(i).draw();
      }
    }
  }

  /**
   * Callback method called each time the mouse is pressed
   */
  @Override
  public void mousePressed() {
    // TODO: traverse the objects list and call mousePressed() method 
    // of the first clicked element ONLY
    for (int i = 0; i < objects.size(); i++) {
      if (objects.get(i).isMouseOver()) {
        objects.get(i).mousePressed();
        break;
      }
    }
  }

  /**
   * Callback method called each time the mouse is released
   */
  @Override
  public void mouseReleased() {
    // TODO:
    // traverse the objects list and call mouseReleased() method 
    // defined for every interactive object
    for (int i = 0; i < objects.size(); i++) {
      if (objects.get(i).isMouseOver()) {
        objects.get(i).mouseReleased();
      }
    }
  }

  /**
   * Callback method called each time the user presses a key
   */
  @Override
  public void keyPressed() {
    // a new Rabbit is added to the CarrotPatch’s objects array 
    // list each time the R-key is pressed
    switch (Character.toUpperCase(this.key)) {
      case 'R':
        // TODO:
        // Add a new Rabbit to this CarrotPath's objects list
        objects.add(new Rabbit());
        break;
      case 'W':
        // TODO:
        // Add a new Wolf to this CarrotPath's objects list
        objects.add(new Wolf());
        break;
     // 7.4 Remove animals from the Carrot Patch
      case 'D':
        // TODO:
        // Remove an animal from this carrot patch if the mouse is over it
        // Traverse the objects list and consider only animal objects in your search
        // Only one animal will be removed. If the mouse is over more than one animal
        // (overlapping animals) when the key-D is pressed, the first one in the objects
        // list (stored at the lowest index) will be removed.
        for (int i = 0; i < objects.size(); i++) {
          if (objects.get(i).isMouseOver() && objects.get(i) instanceof Animal) {
            objects.remove(i);
            break;
          }
        }
    }
  }
  
  /**
   * Removes all the animals and planted carrots from this carrot patch
   */
  public void removeAll() {
    // TODO
    // Remove all the carrots from this CarrotPatch.
    // [Hint] check whether there is a method in the Carrots class which can help implement this
    // behavior
    Carrots.removeAll();

    // Traverse the objects ArrayList and remove only the instances of Animal from the list
    // [Hints]:
    // DO NOT USE a for-each loop, use a normal for loop or a while loop
    // If you use a for loop to traverse the objects list and remove the animals, make sure
    // to decrement the index i each time you remove an animal before going to the next iteration.
    // Otherwise, the next element will be skipped
    for (int i=objects.size()-1; i>=0; i--) { // prevent skip index
      if (objects.get(i) instanceof Animal)
        objects.remove(i);
    }
  }
  
  public static void main(String[] args) {
    
    // 2.1 CarrotPatch’s main() method
    PApplet.main("CarrotPatch"); // do not add any other statement to the main method
    // The PApplet.main() method takes a String input parameter which represents
    // the name of your PApplet class.

  }

}
