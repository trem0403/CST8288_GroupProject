package model;

import enums.Role;

/**
 * Abstract class representing a user in the system.
 * Users can either be Academic Professionals or Academic Institutions.
 * 
 * @author Ethan Tremblay
 */
public abstract class User {
    private int userID;
    private String email;
    private String password;
    private String role;

    /**
     * Default Constructor for creating a User object for user registration.
     * @param userID   The user's ID.
     * @param email    The user's email address.
     * @param password The user's password.
     * @param role     The role of the user (either AcademicProfessional or AcademicInstitution).
     */
    public User(int userID, String email, String password, String role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    /**
     * Constructor for creating a User object for user registration.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     * @param role     The role of the user (either AcademicProfessional or AcademicInstitution).
     */
    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    /**
     * Constructor for creating a User object when fetching all users (either AcademicProfessional or AcademicInstitution).
     * @param userID   The user's ID.
     */
    public User(int userID) {
        this.userID = userID;
    }
    
    

    /** @return The unique identifier of the user. */
    public int getUserID() {
        return userID;
    }

    /** @param userID The unique identifier to set for the user. */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** @return The email address of the user. */
    public String getEmail() {
        return email;
    }

    /** @param email The email address to set for the user. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return The password of the user. */
    public String getPassword() {
        return password;
    }

    /** @param password The password to set for the user. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @return The role of the user. */
    public String getRole() {
        return role;
    }

    /** @param role The role to set for the user. */
    public void setRole(String role) {
        this.role = role;
    }
}
