//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define BankAccount
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

/**
 * Implement the details of BankAccount
 */

public class BankAccount {
  
  private final static String BANK_NAME = "Fantastic Bank"; 
  // private class constant which must be initialized to “Fantastic Bank”
  private static ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
  // private class variable which must be initialized to an empty arraylist of BankAccount
  private static int numberGenerator = 1001;
  // private class variable which must be initialized to 1001
  protected final int NUMBER = numberGenerator + 1;
  // protected constant which must be initialized to the next value of the numberGenerator
  private double balance;
  // private variable which must be initialized in constructor
  
  public BankAccount() {
  
  }
  
  /**
   * constructor
   * 
   * @param initialBalance - an initial balance
   */
  public BankAccount(double initialBalance) throws IllegalArgumentException {
    if (initialBalance < 10.0) throw new IllegalArgumentException("invalid argument");
    // throws an IllegalArgumentException with a descriptive error message 
    // if the provided initialBalance < 10.0
    
    // an initial balance (provided as input)
    this.balance = initialBalance;
    
//    System.out.print("this account NUMBER = " + NUMBER);

    // creates a new bank account with a unique number
    // NUMBER = numberGenerator + 1
    // NUMBER is implicit assigned when object is created
    
    // numberGenerator : the number assigned to the next bank account to be created, 
    //                   must +1 each time a new bank account is created
    BankAccount.numberGenerator++; // NUMBER does not increment
    
//    System.out.println(" -> " + NUMBER);
//    System.out.println(NUMBER + " balance = " + this.balance);
    // adds it to the list of accounts
    BankAccount.accounts.add(this);
  }
  
  /**
   * Adds the specified amount provided as input to the balance of this bank account
   * 
   * @param amount - the amount to deposit
   */
  public void deposit(double amount) throws IllegalArgumentException {
    if (amount <= 0.0) throw new IllegalArgumentException("invalid argument");
    // Throws an IllegalArgumentException if the amount to deposit <= 0.0
    
    // child's balance differs from parent's balance, casting
    if (this instanceof BankAccount) this.balance += amount;
    if (this instanceof MoneyMarketAccount) ((MoneyMarketAccount)this).balance += amount;
  }
  
  /**
   * Withdraws the specified amount from the balance of this bank account
   * 
   * @param amount - the amount to withdraw
   */
  public boolean withdraw(double amount) {
    // return false if the provided amount as input <= 0.0 or 
    // if the balance of this account is NOT enough to complete this withdrawal
    if (amount<=0.0 || this.balance < amount) {
      return false;
    }
    // return true otherwise
    else {
      // child's balance differs from parent's balance, casting
      if (this instanceof BankAccount) this.balance -= amount;
      if (this instanceof MoneyMarketAccount) ((MoneyMarketAccount)this).balance -= amount;
      
      return true;
    }
  }
  
  /**
   * Getter 
   * 
   * @return current balance of this bank account
   */
  public double balance() {
    return this.balance;
  }
  
  /**
   * Checks whether this bank account equals a provided object as input
   * 
   * @return whether this = other
   */
  public boolean equals(Object other) {
    if (other instanceof BankAccount && 
        // whether other is an instance of BANKACCOUNT
        ((BankAccount) other).NUMBER==this.NUMBER) { // whether the same NUMBER
      return true; 
    }
    return false;
  }
  
  /**
   * general object info
   * 
   * @return a String representation of this bank account in the following format
   */
  public String toString() {
    return "Bank Account #" + this.NUMBER + ": $" + this.balance;
  }
  
  /**
   * @return a String representation of all the accounts that have been created
   */
  public static String getAllAccounts() {
    // this string must contain:
    // the name of the bank, and
    // a list of the string representations of all the accounts 
    // stored in the accounts list of the bank
    
    String info = BANK_NAME + "\n";
    for (int i=0; i<accounts.size(); i++) {
      info += accounts.get(i).toString() + "\n";
    }
    return info;
  }
  
  public static void demo() {
    // Runs a demo of this application
    
    // Creates 3 BankAccount accounts
    System.out.println("Create");
    BankAccount bAcc1 = new BankAccount(100.0);
    BankAccount bAcc2 = new BankAccount(100.0);
    BankAccount bAcc3 = new BankAccount(100.0);

    try {
      BankAccount bAcc4 = new BankAccount(0.0); // should trigger exceptions
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    
    // Creates 2 MoneyMarketAccount accounts
    MoneyMarketAccount mAcc1 = new MoneyMarketAccount(100.0);
    MoneyMarketAccount mAcc2 = new MoneyMarketAccount(100.0);
    try {
      MoneyMarketAccount mAcc3 = new MoneyMarketAccount(0.0); 
    }
    catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
//    System.out.println("balance of mAcc1 = " + mAcc1.balance());
    System.out.println();
    
    // check info after creation
    System.out.println(getAllAccounts());
    
    // Deposit
    System.out.println("Deposit");
    double[] toDpst = {100.0, 0.0, 50000.0, -200.0, 400.0};
    for (int i=0; i<toDpst.length; i++) {
      try {
        System.out.println(i);
        System.out.println(BankAccount.accounts.get(i) + " " + " + " + toDpst[i]);
        BankAccount.accounts.get(i).deposit(toDpst[i]);
        System.out.println(BankAccount.accounts.get(i));
      }
      catch(IllegalArgumentException e) {
        System.out.println(e.getMessage());
        System.out.println(BankAccount.accounts.get(i));
      } 
    }
    
    System.out.println();
    
    // Withdrawal
    System.out.println("Withdrawal");
    double[] toWthd = {50.0, 1000.0, 20000.0, 0.0, 450.0};
    for (int i=0; i<toWthd.length; i++) {
      System.out.println(i);
      System.out.println(BankAccount.accounts.get(i) + " " + " - " + toWthd[i]);
      if (!BankAccount.accounts.get(i).withdraw(toWthd[i]))
        System.out.println("withdrawal not successful"); // no exceptions triggered

      System.out.println(BankAccount.accounts.get(i));
    }

    System.out.println();
    
    // Traverses the BankAccount.accounts list 
    // and adds interest to only MoneyMarketAccount accounts
    System.out.println("Traverses and adds interest");
    for (int i=0; i<BankAccount.accounts.size(); i++) {
      BankAccount tgt = BankAccount.accounts.get(i);
      if (tgt instanceof MoneyMarketAccount) {
        
        System.out.println("Before adding interest " + tgt);
        ((MoneyMarketAccount) tgt).addInterest(); // casting then use child's function
        System.out.println("After adding interest " + tgt);
      }
    }
    
    System.out.println();
    
    // Prints a string representation of all the accounts
    System.out.println("check all");
    System.out.println(getAllAccounts());
  }
  
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    demo();
  }

}
