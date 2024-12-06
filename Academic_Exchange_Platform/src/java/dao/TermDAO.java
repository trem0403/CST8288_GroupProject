package dao;

import model.Term;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing Term data in the database.
 * Implements CRUD operations as defined in the GenericDAO interface.
 * 
 * Uses DatabaseConnectionUtil to manage database connections.
 * 
 * @author Ethan Tremblay
 */
public class TermDAO implements GenericDAO<Term> {

    /**
     * Creates a new Term entry in the database.
     *
     * @param term The Term object containing data to be inserted.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void create(Term term) throws SQLException {
        String insertSQL = "INSERT INTO Term (term) VALUES (?)";

        try (var conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, term.getTerm());
            stmt.executeUpdate();

            // Retrieve the generated termID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                term.setTermID(generatedKeys.getInt(1));
            }
        }
    }

    /**
     * Retrieves a Term entry by its ID.
     *
     * @param termID The ID of the Term to retrieve.
     * @return The Term object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public Term getByID(int termID) throws SQLException {
        String selectSQL = "SELECT * FROM Term WHERE termID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            stmt.setInt(1, termID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Term(rs.getInt("termID"), rs.getString("term"));
            }
            return null;
        }
    }

    /**
     * Retrieves all Term entries from the database.
     *
     * @return A list of Term objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<Term> getAll() throws SQLException {
        List<Term> termList = new ArrayList<>();
        String selectAllSQL = "SELECT termID FROM Term ORDER BY termID";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(selectAllSQL); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int termID = rs.getInt("termID");
                termList.add(getByID(termID));
            }
        }
        return termList;
    }

    /**
     * Updates an existing Term entry in the database.
     *
     * @param term The Term object containing updated data.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void update(Term term) throws SQLException {
        String updateSQL = "UPDATE Term SET term = ? WHERE termID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, term.getTerm());
            stmt.setInt(2, term.getTermID());
            stmt.executeUpdate();
        }
    }
} // end of class
