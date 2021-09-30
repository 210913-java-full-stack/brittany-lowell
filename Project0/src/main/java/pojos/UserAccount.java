package pojos;
/**
 * This model class allows the user to access the information stored in the user_account table.
 * The combination of the public methods and the private variables restricts the access the user has to these
 * variables. They have to go through the getter and setter methods to interact with these variables.
 */
public class UserAccount {
    private int id;
    private int account_id;
    private int user_id;

    public UserAccount() {
    }

    public UserAccount(int id) {
        this.id = id;
    }

    public UserAccount(int id, int account_id) {
        this.id = id;
        this.account_id = account_id;
    }

    public UserAccount(int id, int account_id, int user_id) {
        this.id = id;
        this.account_id = account_id;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
