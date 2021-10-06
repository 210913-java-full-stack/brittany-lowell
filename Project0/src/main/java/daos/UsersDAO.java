package daos;

import menus.MainMenu;
import models.Users;
import project0list.BLArrayList;

import java.sql.*;

public class UsersDAO implements DAOInterface<Users>{
    Connection conn;
    MainMenu menu = new MainMenu();

    public UsersDAO() {
        this.conn = menu.getConn();
    }


    /**
     *This method first check whether a row already exists in the database. If it does exist, then this method
     * will UPDATE that row. If it does not exist, this method will INSERT this row into the database.
     */
    @Override
    public void save(Users users) {
        String sql = "SELECT * FROM users WHERE user_id = ?";//Prepares the string with the necessary SQL code.
        try {
            PreparedStatement statement = this.conn.prepareStatement(sql);//Prepares the statement to be sent to the database.
            statement.setInt(1, users.getId()); //Setting up the sql statement with the specified parameters.

            ResultSet results = statement.executeQuery();

            if(results.next()){
                //If this row already exists, update it.
                //Prepares the string with the necessary SQL code.
                String updateStatement = "UPDATE users SET username = ?, password = ? WHERE user_id = ?";
                //Prepares the statement to be sent to the database.
                PreparedStatement preparedUpdateStatement = this.conn.prepareStatement(updateStatement);
                //The next three lines set up the sql statement with the specified parameters.
                preparedUpdateStatement.setString(1, users.getUsername());
                preparedUpdateStatement.setString(2, users.getPassword());
                preparedUpdateStatement.setInt(3, users.getId());
                //Updates the username and password in the users table.
                preparedUpdateStatement.executeUpdate();

            } else {
                //If this row does not already exist, insert it into the table
                //Prepares the string with the necessary SQL code.
                String insertStatement = "INSERT INTO users VALUES (?, ?, ?, ?, ?)";
                //Preparing the statement to be sent to the database and adding the specified parameters
                PreparedStatement preparedInsertStatement = this.conn.prepareStatement(insertStatement);
                preparedInsertStatement.setInt(1, users.getId());
                preparedInsertStatement.setString(2, users.getFirstName());
                preparedInsertStatement.setString(3, users.getLastName());
                preparedInsertStatement.setString(4, users.getUsername());
                preparedInsertStatement.setString(5, users.getPassword());
                //Adds the user_id, first_name, last_name, username, and password to the users table.
                //We need to manually add the account_id since it is not auto incrementing
                //user_id is set in the UserAccountDAO class
                preparedInsertStatement.executeUpdate();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * This method gets one row where user_id is equal to userId.
     * To obtain the desired row input the user_id associate with that row when you call getItem.
     * @return This method returns the row as an object of Users.
     */
    @Override
    public Users getItem(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            PreparedStatement statement = this.conn.prepareStatement(sql); //Use this statement to prepare an SQL string.
            statement.setInt(1, userId);

            ResultSet results = statement.executeQuery();
            /*
            If the executeQuery was successful, the if, then, else block calls the Users constructor and
            saves the information in a Java friendly format.
             */
            if(results.next()){
                return new Users(results.getInt("user_id"), results.getString("first_name"),
                        results.getString("last_name"), results.getString("username"),
                        results.getString("password"));
            } else {
                return null;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
    /**
     *This method gets the entire users table and instantiates it in a BLArrayList object containing
     * Users objects for each row in the table.
     * @return Returns the array list containing the Users objects.
     */
    @Override
    public BLArrayList<Users> getAllItems() {
        String sql = "SELECT * FROM users";
        try {
            BLArrayList<Users> resultsArrayList = new BLArrayList<>();
            /*
            Do not need to prepare a statement if there are no parameters to enter. You just need to create an
            SQL statement.
             */
            Statement statement = this.conn.createStatement();

            ResultSet results = statement.executeQuery(sql);

            while(results.next()) {
                Users users = new Users(results.getInt("user_id"), results.getString("first_name"),
                        results.getString("last_name"), results.getString("username"),
                        results.getString("password"));
                resultsArrayList.add(users);
            }

            return resultsArrayList;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     *This method gets the user_id from the Users model and checks the database for the largest user_id in the table.
     * @return Returns the largest user_id currently in the table.
     */
    public Integer getUserId(){
        //Get the largest user ID in the users table
        String sql = "SELECT MAX(user_id) FROM users";
        int maxUserId;
        try {
            Statement statement = this.conn.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.next();
            maxUserId = results.getInt(1);
            return maxUserId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
