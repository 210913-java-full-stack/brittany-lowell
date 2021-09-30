package daos;

import models.Users;
import project0list.BLArrayList;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO implements DAOInterface<Users>{
    private Connection conn;

    public UsersDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Users users) throws SQLException {

    }

    @Override
    public Users getItem(Users users) throws SQLException {
        return null;
    }

    @Override
    public BLArrayList<Users> getAllItems() throws SQLException {
        return null;
    }
}
