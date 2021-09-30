package daos;

import java.sql.SQLException;

/*
This is one of the interfaces that my DAOs can implement. Uses generics so that the DAO classes can each use their
associated model/POJO classes.
Creates the skeleton structures for the CRUD functions that each DAO will use.
 */
public interface DAOInterface<E> {

    //Create and Update
    //Method for inserting and updating objects to the database
    void save(E e) throws SQLException;

    //Read
    //Method that queries data from the database and fills the empty model object.
    E getItem(E e) throws SQLException;

}
