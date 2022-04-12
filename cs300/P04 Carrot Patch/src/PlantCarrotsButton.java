//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define the plant carrots button
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
 * Implement the details of plant carrot button
 */

class PlantCarrotsButton extends Button {
    public PlantCarrotsButton(float x, float y) {
      super("Plant Carrots", x, y);
//      System.out.println("Create " + this.label);
      }

    // mousePressed() must be overridden
    // if not overriden, Button.mousePressed() would be called
    // these would be called when triggering CarrotPatch.mousePressed()
    @Override
    public void mousePressed() {
      if (this.isMouseOver()) {
//        System.out.println("PlantCarrotsButton Pressed");
      
        // 4.2 Update the implementation of PlantCarrotsButton.mousePressed()
        // plant carrots in all the available spots within the Carrots.carrots array 
        Carrots.plant();
      }
    }
  }