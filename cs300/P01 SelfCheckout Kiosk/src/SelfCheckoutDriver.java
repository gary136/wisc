import java.util.Scanner;

////////////////// PROVIDED CODE /////////////////////////////////////

/**
 * This class implements the driver application for P01 Self-Checkout Kiosk application
 * 
 * @author Mouna
 *
 */
public class SelfCheckoutDriver {
  // Define final parameters
  private static final String WELCOME_MSG =
      "=============   Welcome to the Shopping Cart Checkout App   " + "=============\n";
  private static final String PROMPT_COMMAND_LINE = "ENTER COMMAND: ";
  private static final String MENU =
      "\nCOMMAND MENU:\n" + " [S <index>] Scan one item given its identifier\n"
          + " [C] Display the Checkout summary\n" + " [T] Display the Total\n"
          + " [R <index>] Remove one occurrence of an item given its identifier\n"
          + " [Q]uit the application\n";

  private static final String QUIT_MSG =
      "=============  Thank you for using this App!!!!!  =============";

  /**
   * Displays the checkout summary
   * 
   * @param items array storing the items checked out
   * @param size  number of elements stored in items
   */
  public static void displayCheckoutSummary(String[] items, int size) {
    // get the set of checked items
    String[] itemsSet = new String[size];
    int count = SelfCheckoutKiosk.getUniqueCheckedOutItems(items, size, itemsSet);
    // display the checked out items
    System.out.println("Checkout Summary: ");
    System.out.println("Item_description (#Count)");
    for (int i = 0; i < count; i++) {
      System.out
          .print(itemsSet[i] + " (" + SelfCheckoutKiosk.count(itemsSet[i], items, size) + ")\n");
    }
    System.out.println();
  }

  /**
   * Processes the user command line
   * 
   * @param command user command line
   * @param data    an array of items
   * @param size    number of elements stored in data
   * @return the size of the data array after the user command line is processed
   */
  private static int processUserCommand(String command, String[] data, int size) {
    // parse the command line
    String[] input = command.trim().split(" "); // Array of Strings representing the command

    // process the command with respect to the first character
    // assumption: The format of the user command is correct
    switch (Character.toUpperCase(input[0].charAt(0))) {
      case 'S': // Scan an item
        if (input.length == 2) {
          size = SelfCheckoutKiosk.addItemToBaggingArea(Integer.parseInt(input[1]), data, size);
          // a TypeMisMatchException will be thrown and the program will crash if input[1] is not
          // convertible to the type int
        } else {
          System.out.println("Please enter a valid command line");
        }
        break;
      case 'T': // display number of items checked out together with total
        double subtotal = SelfCheckoutKiosk.getSubTotalPrice(data, size);
        System.out.println("#items: " + size + " SubTotal: $" + String.format("%.2f", subtotal)
            + " Tax: $" + String.format("%.2f", subtotal * SelfCheckoutKiosk.TAX_RATE) + " TOTAL: $"
            + String.format("%.2f", subtotal * (1 + SelfCheckoutKiosk.TAX_RATE)));
        break;
      case 'C': // Display cart content
        displayCheckoutSummary(data, size);
        break;
      case 'R': // Remove one occurrence of a given item from the cart
        if (input.length == 2) {
          size = SelfCheckoutKiosk.remove(SelfCheckoutKiosk.getItemName(Integer.parseInt(input[1])),
              data, size);
        } else {
          System.out.println("Please enter a valid command line");
        }
        break;
      default:
        System.out.println("Please enter a valid command");
    }
    return size;

  }


  /**
   * Runs checkout process
   */
  public static void checkout() {
    // Create oversize one-dimensional array which represents the self-checkout bagging area
    String[] bag = new String[20]; // checkoutBag
    int size = 0; // number of items stored in the checkoutBag

    // Scanner to read user input line commands
    Scanner scanner = new Scanner(System.in);
    // Display menu and prompt user
    System.out.println(MENU);
    System.out.print(PROMPT_COMMAND_LINE);
    // read user command line
    String commandLine = scanner.nextLine().trim(); // input line command

    char command = commandLine.charAt(0);// first character in the user command line
    while (Character.toUpperCase(command) != 'Q') {
      // parse and process user command line
      size = processUserCommand(commandLine, bag, size);
      // Display menu and prompt user
      System.out.println(MENU);
      System.out.print(PROMPT_COMMAND_LINE);
      // read user command line
      commandLine = scanner.nextLine().trim(); // input line command
      command = commandLine.charAt(0);// first character in the user command line
    }
  }

  /**
   * Main method
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {

    // Display Welcome message and prompt user
    System.out.println(WELCOME_MSG);
    // Print the market Catalog
    SelfCheckoutKiosk.printCatalog();
    // start checking out items
    checkout();
    System.out.println(QUIT_MSG);
  }

}