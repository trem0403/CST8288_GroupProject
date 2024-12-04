package controller;

import dao.UserDAO;
import dao.AcademicInstitutionDAO;
import dao.AcademicProfessionalDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AcademicInstitution;
import model.AcademicProfessional;
import model.User;

/**
 * Utility class for servlet-related helper methods. Provides methods for
 * handling user sessions, authentication, and user persistence.
 *
 * @author Ethan Tremblay
 */
public class ServletUtils {

    private static final Logger LOGGER = Logger.getLogger(ServletUtils.class.getName());

    // DAO instances for managing user data
    private static final UserDAO userDAO = new UserDAO();
    private static final AcademicProfessionalDAO academicProfessionalDAO = new AcademicProfessionalDAO();
    private static final AcademicInstitutionDAO academicInstitutionDAO = new AcademicInstitutionDAO();

    /**
     * Inserts a user into the appropriate DAO based on their role.
     *
     * @param user the user object to insert
     * @param response the HttpServletResponse object
     * @param request the HttpServletRequest object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static void insertUser(User user, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        String role = user.getRole(); // Retrieve the role of the user

        // Determine which DAO to use based on the role
        switch (role) {
            case "AcademicProfessional":
                try {
                    // Cast and insert as AcademicProfessional
                    academicProfessionalDAO.create((AcademicProfessional) user);
                    request.setAttribute("message", "Professional successfully registered!");
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Error creating professional", ex);
                }
                break;

            case "AcademicInstitution":
                try {
                    // Cast and insert as AcademicInstitution
                    academicInstitutionDAO.create((AcademicInstitution) user);
                    request.setAttribute("message", "Institution successfully registered!");
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Error creating institution", ex);
                }
                break;

            default:
                LOGGER.log(Level.WARNING, "Invalid user role: {0}", role); // Log a warning if the role is unrecognized
                break;
        }
    }

    /**
     * Fetches the userID based on the provided email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return the userID if found, or -1 if not found or an error occurs
     */
    public static int getUserID(String email, String password) {
        int userID = -1;

        try {
            // Query the DAO for the userID using the provided credentials
            userID = userDAO.getUserID(email, password);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching userID for email: " + email, ex);
        }

        return userID; // Return the userID or -1 if not found
    }

    /**
     * Retrieves the role of a user based on their email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return the user's role, or null if not found
     */
    public static String getRole(String email, String password) {
        String role = null;

        try {
            // Query the DAO for the user's role
            role = userDAO.getRole(email, password);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching role for email: " + email, ex);
        }

        return role; // Return the role or null if not found
    }

    /**
     * Retrieves the userID from the current session.
     *
     * @param request the HttpServletRequest object
     * @return the userID if found, or -1 if not found or an error occurs
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static int getUserIDFromSession(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Fetch the existing session (if any)

        if (session == null || session.getAttribute("userID") == null) {
            LOGGER.warning("Session is null or userID not found in session.");
            return -1;
        }

        try {
            // Attempt to retrieve and cast the userID from the session
            return (Integer) session.getAttribute("userID");
        } catch (ClassCastException ex) {
            LOGGER.log(Level.SEVERE, "Error casting userID from session", ex);
            return -1;
        }
    }

    /**
     * Retrieves the role from the current session.
     *
     * @param request the HttpServletRequest object
     * @return the user's role if found, or null otherwise
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static String getRoleFromSession(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Fetch the existing session (if any)

        if (session == null || session.getAttribute("role") == null) {
            LOGGER.warning("Session is null or role not found in session.");
            return null;
        }

        try {
            // Attempt to retrieve and cast the role from the session
            return (String) session.getAttribute("role");
        } catch (ClassCastException ex) {
            LOGGER.log(Level.SEVERE, "Error casting role from session", ex);
            return null;
        }
    }

    /**
     * Stores userID and role in the session.
     *
     * @param request the HttpServletRequest object
     * @param userID the user's ID
     * @param role the user's role
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static void storeUserInSession(HttpServletRequest request, int userID, String role) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Create or retrieve the session
        session.setAttribute("userID", userID); // Store userID in the session
        session.setAttribute("role", role); // Store role in the session
    }

    /**
     * Validates the current session. If the session is invalid, redirects to
     * the login page.
     *
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @return true if the session is valid, false if redirected to login
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static boolean validateSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Fetch the existing session (if any)

        // Check if the session is null or doesn't contain a userID
        if (session == null || session.getAttribute("userID") == null) {
            response.sendRedirect("login"); // Redirect to login page if session is invalid
            return false;
        }

        return true; // Session is valid
    }
} // end of class
