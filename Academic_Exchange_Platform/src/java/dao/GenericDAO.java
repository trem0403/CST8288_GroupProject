package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Base interface for Data Access Objects (DAOs).
 * Defines common CRUD operations to be implemented by all DAO classes.
 * 
 * @param <T> The type of the entity the DAO handles.
 */
public interface GenericDAO<T> {

    /**
     * Creates a new record in the database.
     *
     * @param entity The entity to be created.
     * @throws SQLException If a database access error occurs.
     */
    void create(T entity) throws SQLException;

    /**
     * Retrieves a record by its ID.
     *
     * @param id The ID of the record to retrieve.
     * @return The entity if found, null otherwise.
     * @throws SQLException If a database access error occurs.
     */
    T getByID(int id) throws SQLException;

    /**
     * Retrieves all records from the database.
     *
     * @return A list of all entities.
     * @throws SQLException If a database access error occurs.
     */
    List<T> getAll() throws SQLException;

    /**
     * Updates an existing record in the database.
     *
     * @param entity The entity with updated data.
     * @throws SQLException If a database access error occurs.
     */
    void update(T entity) throws SQLException;
}
