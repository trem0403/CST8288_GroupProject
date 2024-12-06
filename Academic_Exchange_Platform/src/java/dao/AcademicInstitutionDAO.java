package dao;

import dao.DatabaseConnectionUtil;
import dao.GenericDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.AcademicInstitution;
import model.Course;
import enums.Role;

/**
 * Data Access Object (DAO) for managing AcademicInstitution data in the database.
 * Implements CRUD operations as defined in the BaseDAO interface.
 * 
 * Uses DatabaseConnectionUtil to manage database connections.
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
             PreparedStatement getUserStmt = conn.prepareStatement(getUserSQL)) 
        {

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
    
    
    /**
     * Checks if an academic institution with a given Institution Name ID is already registered.
     * 
     * @param institutionNameID the ID of the institution name to check
     * @return true if the institution is registered, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isInstitutionNameAlreadyRegistered(int institutionNameID) throws SQLException {
        boolean isRegistered = false;
        String validateSQL = "SELECT 1 FROM AcademicInstitution WHERE institutionNameID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(validateSQL)) {
            stmt.setInt(1, institutionNameID);
            try (ResultSet rs = stmt.executeQuery()) {
                isRegistered = rs.next(); // If a result is returned, the institution is already registered
            }
        } 
        return isRegistered;
    }
    
 // Update address for an academic institution
    public void updateAddress(int institutionID, String address, String city, String state, String zip, String country) throws SQLException {
        String updateSQL = "UPDATE AcademicInstitution SET address = ?, city = ?, state = ?, zip = ?, country = ? WHERE institutionID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, address);
            stmt.setString(2, city);
            stmt.setString(3, state);
            stmt.setString(4, zip);
            stmt.setString(5, country);
            stmt.setInt(6, institutionID);
            stmt.executeUpdate();
        }
    }
    
 // Add a course offering for a specific institution
    public void addCourseOffering(Course course) throws SQLException {
        String insertSQL = "INSERT INTO Course (institutionID, title, code, termID, outline, schedule, deliveryMethod, preferredQualifications, compensation) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, course.getInstitutionID());
            stmt.setString(2, course.getTitle());
            stmt.setString(3, course.getCode());
            stmt.setInt(4, course.getTermID());
            stmt.setString(5, course.getOutline());
            stmt.setString(6, course.getSchedule());
            stmt.setString(7, course.getDeliveryMethod());
            stmt.setString(8, course.getPreferredQualifications());
            stmt.setDouble(9, course.getCompensation());
            stmt.executeUpdate();

            // Retrieve the generated course ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                course.setCourseID(generatedKeys.getInt(1));
            }
        }
    }

    /**
     * Retrieves the Institution ID associated with a given Institution Name ID.
     * 
     * @param institutionNameID the ID of the institution name to look up
     * @return the Institution ID if found, or -1 if no matching record is found
     * @throws SQLException if a database access error occurs
     */
    public int getInstitutionIDByNameID(int institutionNameID) throws SQLException {
        int institutionID = -1;
        String query = "SELECT InstitutionID FROM AcademicInstitution WHERE InstitutionNameID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, institutionNameID);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    institutionID = rs.getInt("InstitutionID"); // Retrieve the Institution ID
                }
            }
        }
        return institutionID;
    }
} // end of class