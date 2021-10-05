package daos;

import menus.MainMenu;
import models.UserAccounts;
import project0list.BLArrayList;

import java.sql.*;

public class UserAccountDAO implements UserAccountDAOInterface<UserAccounts> {
    private Connection conn;
    MainMenu menu = new MainMenu();

    public UserAccountDAO() {
        this.conn = menu.getConn();
    }


    /*
       First we need to use a SQL SELECT statement to get the row that we are looking for using the
       PRIMARY KEY of the table. If id = 1 and the table is empty, then this method will INSERT the first row since
       it is guarantied that the item is not already in the table.
       */

    /**
     * This method first check whether a row already exists in the database. If it does exist, then this method
     * will UPDATE that row. If it does not exist, this method will INSERT this row into the database.
     */
    @Override
    public void save(UserAccounts userAccounts) {
        String sql = "SELECT * FROM user_accounts WHERE junction_id = ?";//Prepares the string with the necessary SQL code.
        try {
            PreparedStatement statement = this.conn.prepareStatement(sql);//Prepares the statement to be sent to the database.
            statement.setInt(1, userAccounts.getId()); //Setting up the sql statement with the specified parameters.

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                /*
                If this row already exists, update it.
                //Prepares the string with the necessary SQL code. Only need to update the account_id since user_id
                will already exist if the specified junction_id is in the database.
                */

                //CANNOT CHANGE THE updateStatement BECAUSE IT WILL ASSOCIATE AN ACCOUNT ID WITH THE WRONG USER!!!!!
                String updateStatement = "UPDATE user_accounts SET user_id = ?, account_id = ? WHERE junction_id = ?";
                //Prepares the statement to be sent to the database.
                PreparedStatement preparedUpdateStatement = this.conn.prepareStatement(updateStatement);
                //The next three lines set up the sql statement with the specified parameters.
                preparedUpdateStatement.setInt(1, userAccounts.getUser_id());
                preparedUpdateStatement.setInt(2, userAccounts.getAccount_id());
                preparedUpdateStatement.setInt(3, userAccounts.getId());
                //Updates the username and password in the users table.
                preparedUpdateStatement.executeUpdate();
            } else {
                //If this row does not already exist, insert it into the table
                //Prepares the string with the necessary SQL code.
                String insertStatement = "INSERT INTO user_accounts (user_id, account_id) VALUES (?, ?)";
                //Preparing the statement to be sent to the database and adding the specified parameters
                PreparedStatement preparedInsertStatement = this.conn.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
                preparedInsertStatement.setInt(1, userAccounts.getUser_id());
                preparedInsertStatement.setInt(2,userAccounts.getAccount_id());
                //Adds the user_id, first_name, last_name, username, and password to the users table.
                //We need to manually add the account_id since it is not auto incrementing
                //user_id is set in the UserAccountDAO class
                preparedInsertStatement.executeUpdate();

                ResultSet resultset = preparedInsertStatement.getGeneratedKeys();

                resultset.next();
                userAccounts.setId(resultset.getInt("junction_id") + 1);
                //Can also identify the column by index 1.
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This method gets all account_ids that have the same user_id.
     *
     * @return This method returns an array list since there could be more than one account_id associated with a
     * single user_id.
     * */

    public BLArrayList<UserAccounts> getItem(int userId) {
        //Gets the largest integer in the column: 
        //SELECT MAX(account_id) FROM accounts a
        String sql = "SELECT account_id FROM user_accounts WHERE user_id = ?";
        BLArrayList<UserAccounts> resultsArrayList = new BLArrayList<>();
        try {
            PreparedStatement statement = this.conn.prepareStatement(sql); //Use this statement to prepare a SQL string.
            statement.setInt(1, userId);

            ResultSet results = statement.executeQuery();
            /*
            If the executeQuery was successful, the if, then, else block calls the UserAccounts constructor and
            saves the information in a Java friendly format.
             */
            //Instantiate userAccounts with the data obtained from the database.
            while (results.next()) {
                UserAccounts userAccounts = new UserAccounts(results.getInt("account_id"));
                //Need to save the results to resultsArrayList since there could be multiple account_id's for one user/
                resultsArrayList.add(userAccounts);
            }
            return resultsArrayList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * This method gets the entire user_accounts table and instantiates it in a BLArrayList object containing
     * UserAccounts objects for each row in the table.
     *
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
            //Instantiate userAccounts with the data obtained from the database.
            while (results.next()) {
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

    /**
     * This method gets the junction_id from the database where account_id = 0. It updates the row that
     * was created after the user registered by obtaining that junction_id and calling userAccounts.setId(junction_id).
     * @param userId This method needs the user input. If a Person A registers and Person B creates an account before
     *               Person A, then Person B would not be able to make an account until Person A created their account.
     *               Comparing the userId's and returning 0 if they don't match prevents this issue.
     * @return Returns the junction ID associated with an account_id that = 0
     */
    public Integer getJunctionIdAfterRegistering(int userId){
        String sql = "SELECT junction_id FROM user_accounts WHERE account_id = 0";
        String sql2 = "SELECT user_id FROM user_accounts WHERE account_id = 0";
        int junctionId;
        int databaseUserId;
        try {
            Statement statement = this.conn.createStatement();
            ResultSet results = statement.executeQuery(sql);
            Statement statement2 = this.conn.createStatement();
            ResultSet results2 = statement2.executeQuery(sql2);
            if(results2.next()) {
                databaseUserId = results2.getInt(1);
                System.out.println("this is the user id in the database: " + databaseUserId);

                if(databaseUserId == userId) {
                    if (results.next()) {
                        junctionId = results.getInt(1);
                        System.out.println("this is the junctionID: " + junctionId);
                        return junctionId;
                    } else {
                        return 0;
                    }
                } else{
                    return 0;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return null;
    }
}