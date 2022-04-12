
/**
 * This class models a Box used for inventory
 * 
 * @author mouna
 *
 */
public class Box {
  private static int nextInventoryNumber = 1; // generator of unique inventory numbers
  private Color color; // color of this box
  private final int INVENTORY_NUMBER; // unique inventory number of this box

  /**
   * Creates a new Box and initializes its instance fields color and unique inventory number
   * 
   * @param color color to be assigned of this box. It can be any of the constant color values
   *              defined in the enum Color: Color.BLUE, Color.BROWN, or Color.YELLOW
   */
  public Box(Color color) {
    this.color = color;
    this.INVENTORY_NUMBER = nextInventoryNumber++;
  }

  /**
   * Returns the color of this box
   * 
   * @return the color of this box
   */
  public Color getColor() {
    return color;
  }

  /**
   * returns the inventory number of this box
   * 
   * @return the unique inventory number of this box
   */
  public int getInventoryNumber() {
    return this.INVENTORY_NUMBER;
  }


  /**
   * Returns a String representation of this box in the format "color INVENTORY_NUMBER"
   *
   * @return a String representation of this box
   */
  @Override
  public String toString() {
    return color.toString() + " " + this.INVENTORY_NUMBER;
  }

  /**
   * This method sets the nextInventoryNumber to 1. This method must be used in your tester methods
   * only.
   */
  public static void restartNextInventoryNumber() {
    nextInventoryNumber = 1;
  }
}