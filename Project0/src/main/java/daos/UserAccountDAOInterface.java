package daos;

import project0list.BLArrayList;

import java.sql.SQLException;

public interface UserAccountDAOInterface<E> {

    //Create and Update
    //Method for inserting and updating objects to the database
    void save(E e) throws SQLException;

    //Read
    //Method that queries data from the database and fills the empty model object.
    BLArrayList<E> getItem(int id) throws SQLException;

    BLArrayList<E> getAllItems() throws SQLException;

}
