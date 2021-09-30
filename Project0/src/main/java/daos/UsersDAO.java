package daos;

import pojos.Users;

import java.sql.SQLException;

public class UsersDAO<Users> implements DAOInterface<Users>{


    @Override
    public void save(Users user) throws SQLException {

    }

    @Override
    public Users getItem(Users user) throws SQLException {
        return null;
    }
}
