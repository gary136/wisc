import processing.core.PApplet;

// Do not submit this file on gradescope.
/**
 * This class implements a Button in the Carrot Patch graphic application
 * 
 * @author mouna
 *
 */
public class Button implements GUIListener {
  private static final int WIDTH = 85; // Width of the Button
  private static final int HEIGHT = 32; // Height of the Button
  protected static CarrotPatch processing; // PApplet object where this button will be displayed

  private float[] position; // array storing x and y positions of this Button with respect to the
                            // display window
  protected String label; // text/label that represents the name of this button

  /**
   * Creates a new Button at a given position within a pApplet object
   * 
   * @param label      label to be assigned to this button
   * @param x          x-position where this button will be added to the display window
   * @param y          y-position where this button will be added to the display window
   * @param processing a reference to a PApplet object
   */
  public Button(String label, float x, float y) {
    this.position = new float[2];
    this.position[0] = x;
    this.position[1] = y;
    this.label = label;
  }

  /**
   * Creates a new Button at a given position within a pApplet object
   * 
   * @param x          x-position where this button will be added to the display window
   * @param y          y-position where this button will be added to the display window
   * @param processing a PApplet reference (of specific type )
   */
  public Button(float x, float y) {
    this("Button", x, y);
  }


  /**
   * Sets the PApplet display window where this button is displayed and handled
   * 
   * @param processing the processing to set
   */
  public static void setProcessing(CarrotPatch processing) {
    Button.processing = processing;
  }


  /**
   * Draws this button to the display window
   */
  @Override
  public void draw() {
    processing.stroke(0);// set line value to black
    if (isMouseOver())
      processing.fill(100); // set the fill color to dark gray if the mouse is over the button
    else
      processing.fill(200); // set the fill color to light gray otherwise

    // draw the button (rectangle with a centered text)
    processing.rect(position[0] - WIDTH / 2.0f, position[1] - HEIGHT / 2.0f,
        position[0] + WIDTH / 2.0f, position[1] + HEIGHT / 2.0f);
    processing.fill(0); // set the fill color to black
    processing.text(label, position[0], position[1]); // display the text of the current button
  }

  /**
   * Implements the default behavior of this button when the mouse is pressed. This method must be
   * overridden in the sub-classes to implement a specific behavior if needed.
   */
  @Override
  public void mousePressed() {
//    if (isMouseOver())
//      System.out.println("A button was pressed.");
  }

  /**
   * Implements the default behavior of this button when the mouse is released. This method must be
   * overridden in the sub-classes to implement a specific behavior if needed.
   */
  @Override
  public void mouseReleased() {
//    if (isMouseOver())
//      System.out.println("A button was released.");
  }


  /**
   * Checks whether the mouse is over this button
   * 
   * @return true if the mouse is over this button, false otherwise.
   */
  @Override
  public boolean isMouseOver() {
    return (processing.mouseX > this.position[0] - WIDTH / 2
        && processing.mouseX < this.position[0] + WIDTH / 2
        && processing.mouseY > this.position[1] - HEIGHT / 2
        && processing.mouseY < this.position[1] + HEIGHT / 2);
  }
}