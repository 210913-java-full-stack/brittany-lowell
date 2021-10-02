package daos;

import menus.MainMenu;
import models.UserAccounts;
import models.Users;
import project0list.BLArrayList;

import java.sql.*;

public class UserAccountDAO implements DAOInterface<UserAccounts>{
    private Connection conn;
    MainMenu menu = new MainMenu();

    public UserAccountDAO(Connection conn) {
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
    public void save(UserAccounts userAccounts) throws SQLException {
        String sql = "SELECT * FROM user_accounts WHERE junction_id = ?";//Prepares the string with the necessary SQL code.
        try {
            PreparedStatement statement = this.conn.prepareStatement(sql);//Prepares the statement to be sent to the database.
            statement.setInt(1, userAccounts.getId()); //Setting up the sql statement with the specified parameters.

            ResultSet results = statement.executeQuery();

            if(results.next()){
                /*
                If this row already exists, update it.
                //Prepares the string with the necessary SQL code. Only need to update the account_id since user_id
                will already exist if the specified junction_id is in the database.
                */
                String updateStatement = "UPDATE user_accounts SET account_id = ? WHERE junction_id = ?";
                //Prepares the statement to be sent to the database.
                PreparedStatement preparedUpdateStatement = this.conn.prepareStatement(updateStatement);
                //The next three lines set up the sql statement with the specified parameters.
                preparedUpdateStatement.setInt(1, userAccounts.getAccount_id());
                preparedUpdateStatement.setInt(2, userAccounts.getId());
                //Updates the username and password in the users table.
                preparedUpdateStatement.executeUpdate();

            } else {
                //If this row does not already exist, insert it into the table
                //Prepares the string with the necessary SQL code.
                String insertStatement = "INSERT INTO user_accounts (user_id) VALUES (?)";
                //Preparing the statement to be sent to the database and adding the specified parameters
                PreparedStatement preparedInsertStatement = this.conn.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
                preparedInsertStatement.setInt(1, userAccounts.getUser_id());
                //Adds the user_id, first_name, last_name, username, and password to the users table.
                //We need to manually add the account_id since it is not auto incrementing
                //user_id is set in the UserAccountDAO class
                preparedInsertStatement.executeUpdate();

                ResultSet resultset = preparedInsertStatement.getGeneratedKeys();

                resultset.next();
                userAccounts.setId(results.getInt("junction_id"));
                //Can also identify the column by index 1.
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * This method gets one row where junction_id is equal to junctionID.
     * To obtain the desired row input the junction_id associate with that row when you call getItem.
     * @return This method returns the row as an object of UserAccounts.
     */
    @Override
    public UserAccounts getItem(int junctionId) {
        String sql = "SELECT * FROM user_accounts WHERE junction_id = ?";
        try {
            PreparedStatement statement = this.conn.prepareStatement(sql); //Use this statement to prepare a SQL string.
            statement.setInt(1, junctionId);

            ResultSet results = statement.executeQuery();
            /*
            If the executeQuery was successful, the if, then, else block calls the UserAccounts constructor and
            saves the information in a Java friendly format.
             */
            if(results.next()){
                return new UserAccounts(results.getInt("junction_id"),
                        results.getInt("user_id"), results.getInt("account_id"));
            } else {
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     *This method gets the entire user_accounts table and instantiates it in a BLArrayList object containing
     * UserAccounts objects for each row in the table.
     * @return Returns the array list containing the UserAccounts objects.
     */
    @Override
    public BLArrayList<UserAccounts> getAllItems() {
        String sql = "SELECT * FROM user_accounts";
        try {
            BLArrayList<UserAccounts> resultsArrayList = new BLArrayList<>();
            /*
            Do not need to prepare a statement if there are no parameters to enter. You just need to create a
            SQL statement.
             */
            Statement statement = this.conn.createStatement();

            ResultSet results = statement.executeQuery(sql);

            while(results.next()) {
                UserAccounts userAccounts = new UserAccounts(results.getInt("junction_id"),
                        results.getInt("user_id"), results.getInt("account_id"));
                resultsArrayList.add(userAccounts);
            }

            return resultsArrayList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}