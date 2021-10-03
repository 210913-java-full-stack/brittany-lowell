package daos;

import menus.MainMenu;
import models.Accounts;
import project0list.BLArrayList;

import java.sql.*;

public class AccountsDAO implements DAOInterface<Accounts>{
    private Connection conn;
    MainMenu menu = new MainMenu();

    public AccountsDAO() {
        this.conn = menu.getConn();
    }


    /*
       First we need to use a SQL SELECT statement to get the row that we are looking for using the
       PRIMARY KEY of the table. If id = 1 and the table is empty, then this method will INSERT the first row since
       it is guarantied that the item is not already in the table.
       */

    /**
     *This method first check whether a row already exists in the database. If it does exist, then this method
     * will UPDATE that row. If it does not exist, this method will INSERT this row into the database.
     */
    @Override
    public void save(Accounts accounts) {
        String sql = "SELECT * FROM accounts WHERE accounts_id = ?";//Prepares the string with the necessary SQL code.
        try {
            PreparedStatement statement = this.conn.prepareStatement(sql);//Prepares the statement to be sent to the database.
            statement.setInt(1, accounts.getId()); //Setting up the sql statement with the specified parameters.

            ResultSet results = statement.executeQuery();

            if(results.next()){
                //If this row already exists, update it.
                //Prepares the string with the necessary SQL code.
                String updateStatement = "UPDATE accounts SET balance = ? WHERE account_id = ?";
                //Prepares the statement to be sent to the database.
                PreparedStatement preparedUpdateStatement = this.conn.prepareStatement(updateStatement);
                //The next three lines set up the sql statement with the specified parameters.
                preparedUpdateStatement.setDouble(1,accounts.getBalance());
                preparedUpdateStatement.setInt(2, accounts.getId());
                //Updates the balance in the accounts table.
                preparedUpdateStatement.executeUpdate();

            } else {
                //If this row does not already exist, insert it into the table
                //Prepares the string with the necessary SQL code.
                String insertStatement = "INSERT INTO accounts VALUES (?, ?, ?)";
                //Preparing the statement to be sent to the database and adding the specified parameters
                PreparedStatement preparedInsertStatement = this.conn.prepareStatement(insertStatement);
                preparedInsertStatement.setInt(1, accounts.getId());
                preparedInsertStatement.setString(2, accounts.getAccountType());
                preparedInsertStatement.setDouble(3, accounts.getBalance());
                //Adds the account_id, account type, and balance to the accounts table.
                //We need to manually add the account_id since it is not auto incrementing
                //account_id is set in the UserAccountDAO class
                preparedInsertStatement.executeUpdate();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * This method gets one row where account_id is equal to accountId.
     * To obtain the desired row input the account_id associate with that row when you call getItem.
     * @return This method instantiates an Accounts object and initializes it with data in this row.
     */
    @Override
    public Accounts getItem(int accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try {
            PreparedStatement statement = this.conn.prepareStatement(sql); //Use this statement to prepare a SQL string.
            statement.setInt(1, accountId);

            ResultSet results = statement.executeQuery();
            /*
            If the executeQuery was successful, then this if, then, else block calls the Accounts constructor and
            saves the information in a Java friendly format.
             */
            if(results.next()){
                return new Accounts(results.getInt("account_id"),
                        results.getString("account_type"), results.getDouble("balance"));
            } else {
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     *This method gets the entire accounts table and instantiates it in a BLArrayList object containing
     * Accounts objects for each row in the table.
     * @return Returns the array list containing the Accounts objects.
     */
    @Override
    public BLArrayList<Accounts> getAllItems() {
        String sql = "SELECT * FROM accounts";
        try {
            BLArrayList<Accounts> resultsArrayList = new BLArrayList<>();
            /*
            Do not need to prepare a statement if there are no parameters to enter. You just need to create a
            SQL statement.
             */
            Statement statement = this.conn.createStatement();

            ResultSet results = statement.executeQuery(sql);

            while(results.next()) {
                Accounts accounts = new Accounts(results.getInt("account_id"),
                        results.getString("account_type"), results.getDouble("balance"));
                resultsArrayList.add(accounts);
            }

            return resultsArrayList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     *This method gets the account_id from the Accounts model and checks the database for the largest account_id in the table.
     * @return Returns the largest account_id currently in the table.
     */
    public Integer getAccountId(Accounts accounts){
        String sql = "SELECT MAX(account_id) FROM accounts";
        int maxAccountId;
        try {
            Statement statement = this.conn.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.next();
            maxAccountId = results.getInt("account_id");
            return maxAccountId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
