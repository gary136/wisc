//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define the add rabbit button
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
 * Implement the details of add rabbit button
 */
class AddRabbitButton extends Button {
    public AddRabbitButton(float x, float y) {
      super("Add Rabbit", x, y);
//      System.out.println("Create " + this.label);
    }
    @Override
    public void mousePressed() {
      if (this.isMouseOver()) {
        // 6.2 Add new Rabbits to the carrot patch
        // each time the mouse is pressed and is over a AddRabbitButton object,  
        // a new instance of the Rabbit class is created 
        // and added to the CarrotPatch.objects arraylist 
        Rabbit newRabbit = new Rabbit();
        processing.objects.add(newRabbit);
      }
    }
  }