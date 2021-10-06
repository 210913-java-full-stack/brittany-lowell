package models;

import java.text.NumberFormat;

/**
 * This model class allows the user to access the information stored in the accounts table.
 * The combination of the public methods and the private variables restricts the access the user has to these
 * variables. They have to go through the getter and setter methods to interact with these variables.
 */
public class Accounts {
    private int id;
    private String accountType;
    private double balance;

    public Accounts() {
    }

    public Accounts(int id, String accountType, double balance) {
        this.id = id;
        this.accountType = accountType;
        this.balance = balance;
    }

    /**
     * This method formats the balance from a double to a more human-readable format.
     * @param balance Requires a double that represents the balance
     * @return Returns the formatted balance
     */
    public StringBuffer formatBalance(double balance) {
        /*
        The NumberFormat class contains methods that allow you to add commas to a number. NumberFormat uses a grouping
        size of 3 by default starting to the right of the decimal place.
         */
        StringBuffer displayBalance = new StringBuffer(); //Creating a StringBuffer
        displayBalance.append("$"); //Appending the $ at the beginning of the StringBuffer.
        //Gets the instance of the NumberFormat class by calling NumberFormat.getInstance().
        NumberFormat currencyFormat = NumberFormat.getInstance();
        //Enables grouping which is set to 3 by default.
        currencyFormat.setGroupingUsed(true);
        //Append the formatted balance to the displayBalance StringBuffer.
        displayBalance.append(currencyFormat.format(balance));
        return displayBalance;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}

