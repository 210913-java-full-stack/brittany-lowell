package daos;

import java.sql.SQLException;

public interface DAOCreate<E> {
    //Create and Update
    //Method for inserting and updating objects to the database
    void save(E e) throws SQLException;

}
