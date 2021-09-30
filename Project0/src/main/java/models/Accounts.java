package models;
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

    public Accounts(int id) {
        this.id = id;
    }

    public Accounts(int id, String accountType) {
        this.id = id;
        this.accountType = accountType;
    }

    public Accounts(int id, String accountType, double balance) {
        this.id = id;
        this.accountType = accountType;
        this.balance = balance;
    }

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
