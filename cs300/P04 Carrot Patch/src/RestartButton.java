//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define the restart button
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

// 8 Implement RestartButton.mousePressed() behavior
/**
 * Implement the details of restart button
 */
  class RestartButton extends Button {
    public RestartButton(float x, float y) {
      super("Restart", x, y);
//      System.out.println("Create " + this.label);
    }
    @Override
    public void mousePressed() {
      if (this.isMouseOver()) {
//        System.out.println("RestartButton Pressed");
        processing.removeAll();
      }
    }
  }