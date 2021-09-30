package daos;

import models.Accounts;
import project0list.BLArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountsDAO implements DAOInterface<Accounts>{
    private Connection conn;

    public AccountsDAO(Connection conn) {
        this.conn = conn;
    }


    /*
       First we need to use an SQL SELECT statement to get the row that we are looking for using the
       PRIMARY KEY of the table. If id = 1 and the table is empty, then this method will INSERT the first row since
       it is guarantied that the item is not already in the table.
       */

    /**
     *This method first check whether a row already exists in the database. If it does exist, then this method
     * will UPDATE that row. If it does not exist, this method will INSERT this row into the database.
     */
    @Override
    public void save(Accounts accounts) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE accounts_id = ?";//This line creates a string with the SQL code.
        PreparedStatement statement = conn.prepareStatement(sql);//Prepares the statement to be sent to the database.
        statement.setInt(1, accounts.getId()); //Setting up the sql statement with the specified parameters.

    }

    @Override
    public Accounts getItem(Accounts accounts) throws SQLException {
        return null;
    }

    @Override
    public BLArrayList<Accounts> getAllItems() throws SQLException {
        return null;
    }
}
