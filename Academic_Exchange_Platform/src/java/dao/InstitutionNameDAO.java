package dao;

import dao.DatabaseConnectionUtil;
import dao.GenericDAO;
import model.InstitutionName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing InstitutionName data in the database.
 * Implements CRUD operations as defined in the GenericDAO interface.
 * 
 * Uses DatabaseConnectionUtil to manage database connections.
 * 
 * @author Ethan Tremblay
 */
public class InstitutionNameDAO implements GenericDAO<InstitutionName> {

    /**
     * Creates a new InstitutionName entry in the database.
     *
     * @param institutionName The InstitutionName object containing data to be inserted.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void create(InstitutionName institutionName) throws SQLException {
        String insertSQL = "INSERT INTO InstitutionName (name) VALUES (?)";

        try (var conn = DatabaseConnectionUtil.dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, institutionName.getName());
            stmt.executeUpdate();

            // Retrieve the generated institutionNamesID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                institutionName.setInstitutionNameID(generatedKeys.getInt(1));
            }
        }
    }

    /**
     * Retrieves an InstitutionName entry by its ID.
     *
     * @param institutionNameID The ID of the institution name to retrieve.
     * @return The InstitutionName object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public InstitutionName getByID(int institutionNameID) throws SQLException {
        String selectSQL = "SELECT * FROM InstitutionName WHERE institutionNameID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            stmt.setInt(1, institutionNameID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new InstitutionName(
                    rs.getInt("institutionNamesID"),
                    rs.getString("name")
                );
            }
            return null;
        }
    }

    /**
     * Retrieves all InstitutionName entries from the database.
     *
     * @return A list of InstitutionName objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<InstitutionName> getAll() throws SQLException {
        List<InstitutionName> institutionNamesList = new ArrayList<>();
        String selectAllSQL = "SELECT institutionNameID, name " +
                              "FROM InstitutionName ORDER BY institutionNameID";
        
        try (
            Connection conn = DatabaseConnectionUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(selectAllSQL)) {

      
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int institutionNameID = rs.getInt("institutionNameID");
                    String name = rs.getString("name");
                    
                    institutionNamesList.add(new InstitutionName(institutionNameID, name));
                }
            }
        }    
                return institutionNamesList;
    }

    /**
     * Updates an existing InstitutionName entry in the database.
     *
     * @param institutionName The InstitutionName object containing updated data.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void update(InstitutionName institutionName) throws SQLException {
        String updateSQL = "UPDATE InstitutionName SET name = ? WHERE institutionNameID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, institutionName.getName());
            stmt.setInt(2, institutionName.getInstitutionNameID());
            stmt.executeUpdate();
        }
    }
} // end of class
