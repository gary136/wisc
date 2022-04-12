//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: a simple card matching game
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
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Implement the details of a card matching game
 */
public class MemoryGame {
  
  /* constants */
  
  // 3.1 Declare MemoryGame Static Fields
  //Congratulations message
  private final static String CONGRA_MSG = "CONGRATULATIONS! YOU WON!";
  //Cards not matched message
  private final static String NOT_MATCHED = "CARDS NOT MATCHED. Try again!";
  //Cards matched message
  private final static String MATCHED = "CARDS MATCHED! Good Job!";
  //2D-array which stores cards coordinates on the window display
  private final static float[][] CARDS_COORDINATES =
  new float[][] {{170, 170}, {324, 170}, {478, 170}, {632, 170},
  {170, 324}, {324, 324}, {478, 324}, {632, 324},
  {170, 478}, {324, 478}, {478, 478}, {632, 478}};
  //Array that stores the card images filenames
  private final static String[] CARD_IMAGES_NAMES = new String[] {"ball.png", "redFlower.png",
  "yellowFlower.png", "apple.png", "peach.png", "shark.png"};

  /* variables */
  
  private static PApplet processing; // PApplet object that represents
  //the graphic display window
  private static Card[] cards; // one dimensional array of cards
  private static PImage[] images; // array of images of the different cards
  private static Card selectedCard1; // First selected card
  private static Card selectedCard2; // Second selected card
  private static boolean winner; // boolean evaluated true if the game is won,
  //and false otherwise
  private static int matchedCardsCount; // number of cards matched so far
  //in one session of the game
  private static String message; // Displayed message to the display window
  
  /* methods */
  
  /**
  * Defines the initial environment properties of this game as the program starts
  * @param processing PApplet object that represents the graphic display window
  */
  public static void setup(PApplet processing) {
    // 3.2 Define the setup callback method
    // test this method is being called 
    // System.out.println("setup test");

    // 3.3 Set the background color of the display window
    // 1. Set the processing class variable to the one passed as input parameter.
    MemoryGame.processing = processing;
    
    // 2. Using the processing reference and call the background() method 
    // Set the color used for the background of the Processing window
    // move to draw
    // processing.background(245, 255, 250); // Mint cream color
    
    // 4.1 Create and initialize the array images
    // create images array with the same length as CARD IMAGES NAMES array
    MemoryGame.images = new PImage[CARD_IMAGES_NAMES.length];
    
    // test loading 
    // load ball.png image file as PImage object and store its reference into images[0]
    // images[0] = processing.loadImage("images" + File.separator + CARD_IMAGES_NAMES[0]);
    
    // load all the image files and store their references into the images array
    for (int i=0; i<images.length; i++) {
      images[i] = processing.loadImage("images" + File.separator + CARD_IMAGES_NAMES[i]);
    }
    
    // test drawing
    // Draw an image to the display window
    // call the method image() to draw one image to the center of the screen
    // processing.image(images[0], processing.width / 2, processing.height / 2);
    
    // initiate necessary elements here instead of main
    MemoryGame.startNewGame();
  }
  
  /**
  * Initializes the Game
  */
  public static void startNewGame(){
    
    // 4.2 Define the startNewGame() method
    // initialize the other class variables that we have defined
    MemoryGame.selectedCard1 = null; 
    MemoryGame.selectedCard2 = null;
    MemoryGame.matchedCardsCount = 0; 
    MemoryGame.winner = false; 
    MemoryGame.message = ""; 

    // 4.3 Create cards array
    // create cards array whose length MUST be CARDS COORDINATES.length
    MemoryGame.cards = new Card[CARDS_COORDINATES.length];
    
    // 4.4 Draw one card to the display window
    // test creating card 
    // create an instance of the Card class and stores its reference into 
    // a position of your choice of cards array 
    
    // draws a card facing down at position 0 of CARDS COORDINATES array
    // cards[0] = new Card(images[2], CARDS_COORDINATES[0][0], CARDS_COORDINATES[0][1]);
    // cards[0].draw();
    
    // set the card to be visible before calling its draw() method
    // cards[3] = new Card(images[5], CARDS_COORDINATES[4][0], CARDS_COORDINATES[4][1]);
    // cards[3].setVisible(true);
    // cards[3].draw();
    
    // 4.5 Create and draw the cards
    
    // 4.5.1 Mix up the cards
    // initialize the content of the perfect size array cards such that
    // every PImage reference defined in images array must be assigned to ONLY TWO cards located 
    // at different positions selected randomly from the CARDS COORDINATES array
    int[] mixedUp = Utility.shuffleCards(cards.length);
    
    // 4.5.2 Draw the cards
    // draw the mixed up cards to the screen
    // When created, a card is by default facing down.
    
    // mixedUp[i] guarantees an index of images and not occurs more than 2 times
     for (int i = 0; i <cards.length; i++) {
       cards[i] = new Card(
           images[mixedUp[i]], CARDS_COORDINATES[i][0], CARDS_COORDINATES[i][1]);

       // At this stage, try to set each card visible before drawing it, so you can 
       // see the distribution of cards over the grid when drawn
       // cards[i].setVisible(true); 
       
       // move to draw
       // cards[i].draw();
     }
  }
  
  /**
  * Callback method called each time the user presses a key
  */
  public static void keyPressed() {
    // 5 RESTART THE GAME BY PRESSING N-Key
    // initialize the game with a new distribution of the cards each 
    // time the key ’N’ or ’n’ is pressed.
    
    if (processing.key == 'n' || processing.key == 'N'){ 
      // check which key was pressed 
      // System.out.println("Key-N is pressed");
      
      // reallocate
      MemoryGame.selectedCard1 = null; 
      MemoryGame.selectedCard2 = null;
      MemoryGame.matchedCardsCount = 0; 
      MemoryGame.winner = false; 
      MemoryGame.message = ""; 
       
      MemoryGame.cards = new Card[CARDS_COORDINATES.length];
      int[] mixedUp = Utility.shuffleCards(cards.length);
      for (int i = 0; i <cards.length; i++) {
        cards[i] = new Card(
            images[mixedUp[i]], CARDS_COORDINATES[i][0], CARDS_COORDINATES[i][1]);
      }
    }
  }
  
  // 6.1 Enable selecting cards
  // set a card to be visible and select it when the mouse is pressed and is over the card
  /**
  * Callback method draws continuously this application window display
  */
  public static void draw() {
    // test this method is continuously called
    // System.out.println("drawing test");
    
    // move the statement that sets the background from setup() to draw()
    processing.background(245, 255, 250); // Mint cream color
    
    // Move also the code that draws the different cards from startNewGame() to draw() 
    for (int i = 0; i <cards.length; i++) {
         cards[i].draw();
       }
    
    // Finally, call displayMessage(message) to draw the class variable message 
    displayMessage(MemoryGame.message);
  }
  
  /**
  * Displays a given message to the display window
  * @param message to be displayed to the display window
  */
  public static void displayMessage(String message) {
    processing.fill(0);
    processing.textSize(20);
    processing.text(message, processing.width / 2, 50);
    processing.textSize(12);
  }
  
  /**
  * Checks whether the mouse is over a given Card
  * @return true if the mouse is over the storage list, false otherwise
  */
  public static boolean isMouseOver(Card card) {
    // return true if the mouse is over the image of the card object which
    // reference is passed to it as input parameter, and false otherwise

    // card.getX() and card.getY() return center of the card
    float cardX = card.getX();
    float cardY = card.getY();
    
    // You can access the mouse position through the fields mouseX and mouseY 
    // inside the processing PApplet class field 
    float currX = (float) processing.mouseX;
    float currY = (float) processing.mouseY;

    // you need check if your mouse is within 
    // the boundary(top/bot/left/right) of the image
    // use width and height fields defined within the image 
    // of the card to determine whether the mouse is over it
    float cardW = (float) card.getWidth();
    float cardH = (float) card.getHeight();
    
    if (   currX <= cardX + (cardW / 2.0) // right
        && currX >= cardX - (cardW / 2.0) // left
        && currY <= cardY + (cardH / 2.0) // top
        && currY >= cardY - (cardH / 2.0) // bot
       ) {
      return true;
    }
    else {
      return false;
    }
  }
  
  /**
  * Callback method called each time the user presses the mouse
  */
  public static void mousePressed() {
    // 6.1.2 Select and turn over a card
    // If the mouse is pressed and is over a card laying down
    // , this card must be turned over and selected
    // call displayMessage(message) to draw the class 
    // variable message to the application display window
    
    // only execute when two cards are selected and not matching
    // 2. If they don’t match, turn them back over. 
    // cards are not flipped until the next round, so statements are put outside the loop
    if (selectedCard1 != null && selectedCard2 != null) {
      if (!matchingCards(selectedCard1, selectedCard2)) 
      {
        selectedCard1.setVisible(false);
        selectedCard1.deselect();
        selectedCard1 = null;
        selectedCard2.setVisible(false);
        selectedCard2.deselect();
        selectedCard2 = null;
        MemoryGame.message = NOT_MATCHED;
      }
    }
    
    // use loop to get access of card since no parameter is passed
    for (int i = 0; i <cards.length; i++) {      

      if (isMouseOver(cards[i])) {
        // when the mouse is over a card selected, terminate the iteration to prevent errors
        if (cards[i].isVisible()){
          break;
        }
        
        // if the mouse is over a card not selected, set it visible and select it
        cards[i].setVisible(true);
        cards[i].select();
        
        // mark the chosen card as selectedCard1 or selectedCard2
        if (selectedCard1 == null) {
          MemoryGame.selectedCard1 = cards[i];
          // set message back
          MemoryGame.message = "";
          // prevent triggering the next part 
          break;
        }
        
        if (selectedCard1 != null && selectedCard2 == null) {
          MemoryGame.selectedCard2 = cards[i];
          // 1. If two cards are selected and they match, keep them visible 
          // , and set them to be matched
          if (matchingCards(selectedCard1, selectedCard2)) {
            selectedCard1.setMatched(true);
            selectedCard1.deselect();
            selectedCard1 = null;
            selectedCard2.setMatched(true);
            selectedCard2.deselect();
            selectedCard2 = null;
            // need to update the message and matchedCardsCount here
            MemoryGame.message = MATCHED;
            MemoryGame.matchedCardsCount+=2;
          }
          else {
            // need to update the message here
            MemoryGame.message = NOT_MATCHED;
          }
           break;
        }
      }
    }
    
    // check if all cards have match
    if (MemoryGame.matchedCardsCount==cards.length) {
      MemoryGame.winner = true;
      MemoryGame.message = CONGRA_MSG;
    }
  }
  
  // 6.2 Matching cards
  
  /**
  * Checks whether two cards match or not
  * @param card1 reference to the first card
  * @param card2 reference to the second card
  * @return true if card1 and card2 image references are the same, false otherwise
  */
  public static boolean matchingCards(Card card1, Card card2) {
    // To get a reference to the image of the card, 
    // call the instance method getImage() defined in Card class
    if (card1.getImage().equals(card2.getImage())) {
      return true;
    }
    return false;
  }
  
  public static void main(String[] args) {
    Utility.startApplication(); // starts the application
  }
}