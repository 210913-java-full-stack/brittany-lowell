package daos;

import models.UserAccount;
import project0list.BLArrayList;

import java.sql.Connection;
import java.sql.SQLException;

public class UserAccountDAO implements DAOInterface<UserAccount> {
    private Connection conn;

    public UserAccountDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(UserAccount userAccount) throws SQLException {

    }

    @Override
    public UserAccount getItem(UserAccount userAccount) throws SQLException {
        return null;
    }

    @Override
    public BLArrayList<UserAccount> getAllItems() throws SQLException {
        return null;
    }
}
