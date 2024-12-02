package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class for handling operations related to the User table in the database.
 * Provides methods to check email registration, authenticate users, and retrieve user roles.
 * 
 * Uses DatabaseConnectionUtil to manage database connections.
 * 
 * @author Ethan Tremblay
 */
public class UserDAO {

    /**
     * Checks if an email is already registered in the database.
     * 
     * @param email the email to check for registration
     * @return true if the email is registered, false otherwise
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Authenticates a user based on the provided email and password.
     * 
     * @param email the user's email
     * @param password the user's password
     * @return true if the credentials are valid, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean authenticate(String email, String password) throws SQLException {
        boolean isValid = false;
        String authenticateSQL = "SELECT 1 FROM User WHERE email = ? AND password = ?";
        
        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(authenticateSQL)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                isValid = rs.next(); // If a result is returned, the credentials are valid
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * Retrieves the role of a user based on their email and password.
     * 
     * @param email the user's email
     * @param password the user's password
     * @return the user's role as a String, or null if the user is not found
     * @throws SQLException if a database access error occurs
     */
    public String getRole(String email, String password) throws SQLException {
        String role = null;
        String getRoleSQL = "SELECT role FROM User WHERE email = ? AND password = ?";
        
        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(getRoleSQL)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // Check if a row is available
                    role = rs.getString("role"); // Fetch the role
                }
            }
        }
        return role; // Returns null if no matching user is found
    }
    
    /**
     * Retrieves the id of a user based on their email and password. This is for session handling
     * 
     * @param email the user's email
     * @param password the user's password
     * @return the user's id as a String, or null if the user is not found. 
     * @throws SQLException if a database access error occurs
     */
    public String getUserID(String email, String password) throws SQLException {
        String role = null;
        String getRoleSQL = "SELECT userID FROM User WHERE email = ? AND password = ?";
        
        try (Connection conn = DatabaseConnectionUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(getRoleSQL)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // Check if a row is available
                    role = rs.getString("userID"); // Fetch the userID
                }
            }
        }
        return role; // Returns null if no matching user is found
    }
    
    
} // end of class
