package dao;

import dao.DatabaseConnectionUtil;
import dao.GenericDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.AcademicInstitution;
import enums.Role;

/**
 * Data Access Object (DAO) for managing AcademicInstitution data in the database.
 * Implements CRUD operations as defined in the BaseDAO interface.
 * 
 * @author Ethan Tremblay
 */
public class AcademicInstitutionDAO implements GenericDAO<AcademicInstitution> {

    /**
     * Creates a new AcademicInstitution in the database.
     *
     * @param institution The AcademicInstitution object containing data to be inserted.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void create(AcademicInstitution institution ) throws SQLException {
      
        String insertUserSQL = "INSERT INTO User (email, password, role) VALUES (?, ?, ?)";
        String insertInstitutionSQL = "INSERT INTO AcademicInstitution (institutionID, institutionNameID) VALUES (?, ?)";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement insertUserStmt = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertInstitutionStmt = conn.prepareStatement(insertInstitutionSQL)) {
            
             // Insert the user into the User table
            insertUserStmt.setString(1, institution.getEmail());
            insertUserStmt.setString(2, institution.getPassword());
            insertUserStmt.setString(3, institution.getRole());
            insertUserStmt.executeUpdate();
            
             // Get the generated userID
            ResultSet generatedKeys = insertUserStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                institution.setUserID(generatedKeys.getInt(1)); // Set institution ID as user ID
                
                // Insert the academic institution data
                insertInstitutionStmt.setInt(1, institution.getUserID());
                insertInstitutionStmt.setInt(2, institution.getInstitutionNameID());
                insertInstitutionStmt.executeUpdate();
            }            
        }
    }

     /**
     * Retrieves an academic institution by ID.
     *
     * @param institutionID The ID of the academic institution to retrieve.
     * @return The AcademicInstitution object.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public AcademicInstitution getByID(int institutionID) throws SQLException {
        String getInstitutionSQL = "SELECT * FROM AcademicInstitution WHERE institutionID = ?";
        String getUserSQL = "SELECT * FROM User WHERE userID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement getInstitutionStmt = conn.prepareStatement(getInstitutionSQL);
             PreparedStatement getUserStmt = conn.prepareStatement(getUserSQL)) {

            // Get the academic institution
            getInstitutionStmt.setInt(1, institutionID);
            ResultSet institutionRS = getInstitutionStmt.executeQuery();

            if (institutionRS.next()) {
                int userID = institutionRS.getInt("institutionID");

                // Get the user details for the institution
                getUserStmt.setInt(1, userID);
                ResultSet userRS = getUserStmt.executeQuery();
                
                if (userRS.next()) {
                    return new AcademicInstitution(
                        userID,
                        userRS.getString("email"),
                        userRS.getString("password"),
                        userRS.getString("role"),    
                        institutionRS.getInt("institutionNameID"),
                        institutionRS.getString("zip")
                    );
                }
            }
            return null;
        } 
    }
   
    

    /**
     * Retrieve records of all AcademicInstitution users.
     *
     * 
     * @return A list of AcademicInstitution objects containing the institution details.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<AcademicInstitution> getAll() throws SQLException {
        List<AcademicInstitution> institutions = new ArrayList<>();
        String getAllInstitutionsSQL = "SELECT institutionID, institutionNameID, zip, " +
                                       "FROM AcademicInstitution ORDER BY institutionID";
        
        try (
            Connection conn = DatabaseConnectionUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(getAllInstitutionsSQL)) {
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int institutionID = rs.getInt("institutionID");
                    int institutionNameID = rs.getInt("institutionNameID");
                    String zip = rs.getString("zip");

                    institutions.add(new AcademicInstitution(institutionID, institutionNameID, zip));
                }
            }
        }
        return institutions;
    }

    /**
     * Updates an academic institution and the associated user.
     *
     * @param institution The AcademicInstitution object to be updated.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void update(AcademicInstitution institution) throws SQLException {
        String updateUserSQL = "UPDATE User SET email = ?, password = ?, role = ? WHERE userID = ?";
        String updateInstitutionSQL = "UPDATE AcademicInstitution SET institutionNameID = ?, zip = ? WHERE institutionID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement updateUserStmt = conn.prepareStatement(updateUserSQL);
             PreparedStatement updateInstitutionStmt = conn.prepareStatement(updateInstitutionSQL)) {

            // Update the user data
            updateUserStmt.setString(1, institution.getEmail());
            updateUserStmt.setString(2, institution.getPassword());
            updateUserStmt.setString(3, institution.getRole());
            updateUserStmt.setInt(4, institution.getUserID());
            updateUserStmt.executeUpdate();

            // Update the academic institution data
            updateInstitutionStmt.setInt(1, institution.getInstitutionNameID());
            updateInstitutionStmt.setString(2, institution.getZip());
            updateInstitutionStmt.setInt(3, institution.getUserID());
            updateInstitutionStmt.executeUpdate();
        } 
    }
    
    public boolean isEmailAlreadyRegistered(String email) throws SQLException {
    boolean isRegistered = false;
    String validateSQL = "SELECT 1 FROM User WHERE email = ?";

    try (Connection conn = DatabaseConnectionUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(validateSQL)) {
        stmt.setString(1, email);
        try (ResultSet rs = stmt.executeQuery()) {
            isRegistered = rs.next(); // If a result is returned, the email is registered
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return isRegistered;
}

} // end of class