//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define the add wolf button
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
 * Implement the details of add wolf button
 */

class AddWolfButton extends Button {
    public AddWolfButton(float x, float y) {
      super("Add Wolf", x, y);
//      System.out.println("Create " + this.label);
    }
    @Override
    public void mousePressed() {
      if (this.isMouseOver()) {
//        System.out.println("AddWolfButton Pressed");
        // 7.3 Add new Wolves to the Carrot Patch
        Wolf newWolf = new Wolf();
        processing.objects.add(newWolf);
      }
    }
  }