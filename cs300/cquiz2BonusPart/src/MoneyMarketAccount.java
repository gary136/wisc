//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: define MoneyMarketAccount
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
 * Implement the details of MoneyMarketAccount
 */
public class MoneyMarketAccount extends BankAccount{
  private static final double PENALTY_RATE = 0.1;
  // a private class constant which represents the penalty of withdrawn
  private static final double INTEREST_RATE = 0.05;
  // a private class constant which represents the interest rate
  double balance;
  // since unable to access parent's private member, define self variable
  
  /** 
   * constructor
   * 
   * @param initialBalance - an initial balance
  */
  public MoneyMarketAccount(double initialBalance) throws IllegalArgumentException 
  {
    // creates a new money market bank account similar with parent
    super(initialBalance); // covers most requirements
                           
    this.balance = initialBalance; // child distinct here
}  
  
  /** 
   * Adds interest (interest * balance) to the balance of 
   * this account with respect to the INTEREST RATE 
  */
  public void addInterest() {
    double interest = INTEREST_RATE * this.balance();
    this.balance+=interest;
  }
  
  /** 
   * Withdraws the specified amount, plus the penalty for withdrawing 
   * from this money market account with respect to PENALTY RATE
   * 
   * @param amount - the amount to withdraw
  */
  @Override
  public boolean withdraw(double amount) {
    double penalty = amount * PENALTY_RATE;
    amount+=penalty;
    return super.withdraw(amount);
  }
  
  /** 
   * general object info
   * 
   * @return a String representation of this bank account in the following format
  */
  @Override
  public String toString() {
    return "Money Market Account #" + NUMBER + ": $" + this.balance;
  }
}
