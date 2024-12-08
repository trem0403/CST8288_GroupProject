package controller;

import dao.UserDAO;
import dao.AcademicInstitutionDAO;
import dao.AcademicProfessionalDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ethan Tremblay
 */
public class ValidationUtils {

    private static final Logger LOGGER = Logger.getLogger(ServletUtils.class.getName());

    // DAO instances for managing user data
    private static final UserDAO userDAO = new UserDAO();
    private static final AcademicProfessionalDAO academicProfessionalDAO = new AcademicProfessionalDAO();
    private static final AcademicInstitutionDAO academicInstitutionDAO = new AcademicInstitutionDAO();

    public static String emailValidation(String email) {

        String emailError = null;

        // Check if email format is valid
        if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            emailError = "Invalid email format";
        }

        // Check if email is already registered
        try {
            if (userDAO.isEmailAlreadyRegistered(email)) {
                emailError = "The email is already registered. Please use a different email.";
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicInstitutionRegistrationServlet.class.getName()).log(Level.SEVERE, "Error with query", ex);
            emailError = "An error occurred while checking the email. Please try again.";
        }

        return emailError;
    }

    public static String passwordValidation(String password) {

        String passwordError = null;

        // Check if password length is valid
        if (password.length() < 6) {
            passwordError = "Password must be at least 6 characters";
        }

        return passwordError;

    }

    public static String nameValidation(String name) {

        String nameError = null;

        // Check if name format is valid
        if (!name.matches("^[A-Za-z\\s]+$")) {
            nameError = "Name must only use letters and spaces";
        }

        return nameError;
    }

    public static String academicPositionValidation(String academicPosition) {

        String academicPositionError = null;

        
        // Check if academic position format is valid
        if (!academicPosition.matches("^[A-Za-z\\s]+$")) {
            academicPositionError =  "Academic Position must only use letters and spaces";
        } 
        
        return academicPositionError;
    }

    public static String institutionValidation(int institutionNameID) {

        String institutionError = null;

        // Check if the institution is already registered
        try {
            if (academicInstitutionDAO.isInstitutionNameAlreadyRegistered(institutionNameID)) {
                institutionError = "An institution with this name is already registered.";
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicInstitutionRegistrationServlet.class.getName()).log(Level.SEVERE, "Error checking institution", ex);
            institutionError = "An error occurred while checking the institution. Please try again.";
        }

        return institutionError;
    }
} // end of class
