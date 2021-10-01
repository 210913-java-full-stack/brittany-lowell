package daos;

import models.UserAccounts;
import project0list.BLArrayList;

import java.sql.Connection;
import java.sql.SQLException;

public class UserAccountDAO implements DAOInterface<UserAccounts> {
    private Connection conn;

    public UserAccountDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(UserAccounts userAccounts) throws SQLException {

    }

    @Override
    public UserAccounts getItem(int id) throws SQLException {
        return null;
    }

    @Override
    public BLArrayList<UserAccounts> getAllItems() throws SQLException {
        return null;
    }
}
