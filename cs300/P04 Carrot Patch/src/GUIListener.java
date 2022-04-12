
// DO NOT SUBMIT this file to gradescope
/**
 * This interface represents the abstract data type which models any interactive visible reference
 * type which can be drawn and responds to the mouse actions in a processing graphic interface
 * 
 * @author Mouna
 *
 */
public interface GUIListener {
  /**
   * draws this interactive object to the display window
   */
  public void draw();

  /**
   * called each time the mouse is Pressed
   */
  public void mousePressed();

  /**
   * called each time the mouse is Pressed
   */
  public void mouseReleased();

  /**
   * checks whether the mouse is over this GUI listener
   * 
   * @return true if the mouse is over this interactive object, false otherwise
   */
  public boolean isMouseOver();

}