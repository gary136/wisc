//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: (descriptive title of the program making use of this file)
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

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple menu-based application to use your Person and Room classes.
 * <p>
 * Bugs: empty input at the prompt will cause an IndexOutOfBoundsException that I don't feel like
 * protecting against so Make Smart Choices
 * @author hobbes
 */
public class TrackingApp {
  private static ArrayList<Person> people = new ArrayList<Person>();
  private static ArrayList<Room> rooms = new ArrayList<Room>();
  
  private static void printTopMenu() {
    System.out.println("TRACKING APP MENU\n=================");
    System.out.println("A) Admin functions");
    System.out.println("B) Check in/out");
    System.out.println("C) Quick example setup");
    System.out.println("D) Quit");
    System.out.print("> ");
  }
  
  private static void printAdminMenu() {
    System.out.println("ADMIN FUNCTIONS\n===============");
    System.out.println("1) Add new room");
    System.out.println("2) Check room occupancy");
    System.out.println("3) Create new person");
    System.out.print("> ");
  }
  
  private static void printCheckMenu() {
    System.out.println("CHECK IN/OUT MENU\n=================");
    System.out.println("1) Check person in");
    System.out.println("2) Check person out");
    System.out.println("3) List all people");
    System.out.println("4) List all rooms");
    System.out.print("> ");
  }
  
  private static void listAllPeople() {
    for (int i=0; i<people.size(); i++) {
      System.out.println("  "+(i+1)+") "+people.get(i).getName());
    }
  }
  
  private static void listAllRooms() {
    String[] names = Room.getNames();
    for (int i=0; i<names.length; i++) {
      System.out.println("  "+(i+1)+") "+names[i]);
    }
  }
  
  private static void addNewRoom(Scanner in) {
    System.out.print("Room name: ");
    String roomName = in.nextLine();
    System.out.print("Normal capacity: ");
    int cap = in.nextInt();
    in.nextLine();
    try {
      rooms.add(new Room(roomName, cap));
    }
    catch (IllegalArgumentException e) {
      System.out.println("ERROR: Unable to create room "+roomName+" with capacity "+cap);
    }
  }

  private static void checkRoomOccupancy(Scanner in) {
    listAllRooms();
    System.out.print("Which room: ");
    int roomNum = in.nextInt()-1;
    in.nextLine();
    try {
      System.out.print(rooms.get(roomNum));
    }
    catch (Exception e) {
      System.out.println("ERROR: Invalid room");
    }
  }
  
  private static void createNewPerson(Scanner in) {
    System.out.print("Person name: ");
    String personName = in.nextLine();
    people.add(new Person(personName));
  }
  
  private static void checkPersonIn(Scanner in) {
    // check
    for (int i=0;i<people.size();i++) {
      System.out.print(people.get(i).getName() + " ");
    }
    System.out.println("\n");
    
    int personIndex = -1;
    int roomIndex = -1;
    while (roomIndex < 0) {
      System.out.print("Which room number? (-1 to see a list of all rooms): ");
      roomIndex = in.nextInt()-1;
      in.nextLine();
      if (roomIndex == -2) {
        listAllRooms();
      }
    }
    while (personIndex < 0) {
      System.out.print("Which person number? (-1 to see a list of all people): ");
      personIndex = in.nextInt()-1;
      in.nextLine();
      if (personIndex == -2) {
        listAllPeople();
      }
      else if (personIndex >= 0 && personIndex < people.size() &&
          !people.get(personIndex).isWaiting()) {
        System.out.println("ERROR: Person "+people.get(personIndex).getName()+
            " is already in a room");
        personIndex = people.size();
      }
    }
    if (personIndex < people.size()) {
      // check them in
      if (rooms.get(roomIndex).checkIn(people.get(personIndex))) {
        System.out.println("Successfully checked "+people.get(personIndex).getName()+
            " into room "+Room.getNames()[roomIndex]);
      }
      if (people.get(personIndex).isWaiting()) {
        System.out.println("...but they think they're still waiting. Hmm! A bug??");
      }
    }
  }
 
  private static void checkPersonOut(Scanner in) {
    int personIndex = -1;
    int roomIndex = -1;
    while (roomIndex < 0) {
      System.out.print("Which room number? (-1 to see a list of all rooms): ");
      roomIndex = in.nextInt()-1;
      in.nextLine();
      if (roomIndex == -2) {
        listAllRooms();
      }
    }

    // check
    for (int i=0;i<people.size();i++) {
      System.out.print(people.get(i).getName() + " ");
    }
    System.out.println("\n");
    
    while (personIndex < 0) {
      
      System.out.print("Which person? (-1 to see a list of all people): ");
      personIndex = in.nextInt()-1;

      
      in.nextLine();
      if (personIndex == -2) {
        listAllPeople();
      }
      else if (personIndex >= 0 && personIndex < people.size() &&
          people.get(personIndex).isWaiting()) {

//        // check
//        System.out.println("personIndex = " + personIndex + ", people.size() = " + people.size());
//        System.out.println("person = " + people.get(personIndex).getName());
//        System.out.println("state = " + people.get(personIndex).isWaiting());
        
        System.out.println("ERROR: Person "+people.get(personIndex).getName()+
            " is not in a room");
        personIndex = people.size();
      }
    }
    if (personIndex < people.size()) {
      // check them out
      if (rooms.get(roomIndex).checkOut(people.get(personIndex))) {
        System.out.println("Successfully checked "+people.get(personIndex).getName()+
            " out of room "+Room.getNames()[roomIndex]);
      }
      if (!people.get(personIndex).isWaiting()) {
        System.out.println("...but they don't think they're waiting. Hmm! A bug??");
      }
    }
  }
  
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    char userIn = '\0';
    
    while (true) {
      // TOP MENU
      printTopMenu();
      userIn = in.nextLine().toUpperCase().charAt(0);
      
      switch (userIn) {
      case 'A': // ADMIN MENU
        printAdminMenu();
        userIn = in.nextLine().charAt(0);
        switch (userIn) {
        case '1': // add new room
          addNewRoom(in);
          break;
        case '2': // check room occupancy
          checkRoomOccupancy(in);
          break;
        case '3': // create new person
          createNewPerson(in);
          break;
        default:
          System.out.println("Invalid input.");
          break;
        }
        break;
      
      case 'B': // CHECK IN/OUT MENU
        printCheckMenu();
        userIn = in.nextLine().charAt(0);
        switch (userIn) {
        case '1': // check person in
          checkPersonIn(in);
          break;
        case '2': // check person out
          checkPersonOut(in);
          break;
        case '3': // list all people
          listAllPeople();
          break;
        case '4': // list all rooms
          listAllRooms();
          break;
        default:
          break;
        }
        break;

      case 'C': // quick functions
        people.add(new Person("Mouna"));
        people.add(new Person("Hobbes"));
        people.add(new Person("Michelle"));
        people.add(new Person("Sulong"));
        
        rooms.add(new Room("CS 1240", 7));
        rooms.add(new Room("AG 100", 10));
        
        rooms.get(0).checkIn(people.get(0));
        rooms.get(0).checkIn(people.get(1));
        rooms.get(0).checkIn(people.get(2));
        break;
        
      case 'D': // GTFO
        System.out.println("Good-bye!");
        in.close();
        System.exit(0);
      
      default:
        System.out.println("Invalid input.");
        break;
      }
    }
  }

}
