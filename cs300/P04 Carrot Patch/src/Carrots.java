import java.io.File;
import processing.core.PApplet;
import processing.core.PImage;

//DO NOT SUBMIT this file to gradescope
/**
 * This class models a list of carrots in a carrot patch. This class is not instantiable. It
 * implements static methods ONLY.
 * 
 * @author mouna
 *
 */
public class Carrots {
  private static PApplet processing; // a reference to display window where these carrots will be
                                     // drawn
  private static PImage carrotImage; // carrot's image
  private static int[][] positions = new int[][] { // x,y positions of carrots if they are planted
      {145, 270}, {185, 305}, {230, 345}, {280, 390}, {330, 435}, {380, 475}, {425, 515},
      {475, 550}, {300, 550}, {250, 515}, {205, 475}, {155, 435}, {105, 390}, {60, 345}, {20, 305},
      {15, 475}, {50, 515}, {90, 550}};
  private static PImage[] carrots; // perfect size array storing planted carrots

  /**
   * Sets the processing PApplet graphic window, uploads the image of carrots, and creates the
   * carrots array. The carrots array must have the same length of positions and contains only null
   * references when created.
   * 
   * @param processing reference to the pApplet graphic display window of this application
   */
  public static void settings(PApplet processing) {
    Carrots.processing = processing;
    carrotImage = processing.loadImage("images" + File.separator + "carrot.png");
    carrots = new PImage[positions.length];
  }

  /**
   * Plants carrots in the carrot patch
   */
  public static void plant() {
    // traverse the carrots array and set every null reference to the carrot's image
    for (int i = 0; i < carrots.length; i++) {
      if (carrots[i] == null) {
        carrots[i] = carrotImage;
      }
    }
  }

  /**
   * Draws the planted carrots (the images of the carrots stored in the carrots array) on the
   * processing display window of this application carrots[i] must be drawn to the (x,y)
   * positions[i][0], positions[i][1] location
   */
  public static void draw() {
    for (int i = 0; i < carrots.length; i++) {
      if (carrots[i] != null) {
        processing.image(carrotImage, positions[i][0], positions[i][1]);
      }
    }
  }


  /**
   * Gets information about the first non-null carrot (stored at the lowest-index).
   * 
   * @return a perfect-size array of ints which contains exactly 3 elements. The element at index 0
   *         represents the index of the carrot in the carrots array. The elements at index 1 and at
   *         index 2 represent the x-position and the y-position of the carrot in the display window
   *         respectively. If the carrots array is empty, this method returns null.
   */
  public static int[] getFirstCarrot() {
    for (int i = 0; i < carrots.length; i++) {
      if (carrots[i] != null) {
        return new int[] {i, positions[i][0], positions[i][1]};
      }
    }
    return null;
  }

  /**
   * Removes the carrot at the specified position (sets its reference to null)
   * 
   * @param index index of the element to remove from the carrots array
   * @throws an IndexOutOfBoundsException with a descriptive error message if if the index is out of
   *            range (index < 0 || index >= carrots.length)
   */
  public static void remove(int index) {
    if (index < 0 || index >= carrots.length)
      throw new IndexOutOfBoundsException("Error! Index out of bounds.");
    carrots[index] = null;
  }

  /**
   * Removes all the planted carrots from the carrots array
   */
  public static void removeAll() {
    carrots = new PImage[positions.length];
  }


}