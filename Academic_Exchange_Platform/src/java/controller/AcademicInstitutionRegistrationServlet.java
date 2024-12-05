package controller;

import dao.DatabaseConnectionUtil;

import dao.AcademicInstitutionDAO;
import model.AcademicInstitution;

import dao.InstitutionNameDAO;
import model.InstitutionName;

import dao.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ethan Tremblay
 */
@WebServlet(name = "AcademicInstitutionRegistrationServlet", urlPatterns = "/institutionRegister")
public class AcademicInstitutionRegistrationServlet extends HttpServlet {


    private AcademicInstitutionDAO academicInstitutionDAO;
    private InstitutionNameDAO institutionNameDAO;
    private UserDAO userDAO;

  

    /**
     * Initializes the servlet and sets up the DAO instance. This method is
     * called once when the servlet is first loaded.
     *
     * @throws ServletException if an initialization error occurs
     */
    @Override
    public void init() throws ServletException {
        // Initialize the DAO's
        academicInstitutionDAO = new AcademicInstitutionDAO();
        institutionNameDAO = new InstitutionNameDAO();
        userDAO = new UserDAO();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        sendInstitutionNameList(request, response);

        // Forward the request and response to the JSP page for rendering the registration form.
        request.getRequestDispatcher("WEB-INF/views/academic_institution_registration.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int institutionNameID = Integer.parseInt(request.getParameter("institutionNameID"));

        // Define role explicitly
        String role = "AcademicInstitution";

        // Initialize and declare error messages
        String emailError = ValidationUtils.emailValidation(email);
        String passwordError = ValidationUtils.passwordValidation(password);
        String institutionError = ValidationUtils.institutionValidation(institutionNameID);
    
        // If there are errors, set them as request attributes and forward back to the JSP
        if (emailError != null || passwordError != null || institutionError != null ) {
            request.setAttribute("email-error", emailError);
            request.setAttribute("password-error", passwordError);
            request.setAttribute("institution-error", institutionError);

            // Add form data to preserve user input
            request.setAttribute("email", email);
            request.setAttribute("password", password);

            sendInstitutionNameList(request, response);

            // Forward to the registration page again
            request.getRequestDispatcher("WEB-INF/views/academic_institution_registration.jsp").forward(request, response);
            return;
        } else {
            // Continue with the registration process (e.g., save the data to the database)
            // If successful, redirect to a success page

            // Create the AcademicInstitution object
            AcademicInstitution academicInstitution = new AcademicInstitution(email, password, role, institutionNameID);

            // Insert into database
            ServletUtils.insertUser(academicInstitution);
            
            // Fetch userID for session handling
            int userID = ServletUtils.getUserID(email, password);
            
            /*
            * After successful registration and fetching userID,
            * Store useriD and role into a session.
            */
            ServletUtils.storeUserInSession(request, userID, role);
            
            // Redirect to profile setup
            response.sendRedirect("institutionProfile"); 
        }
    }

    protected void sendInstitutionNameList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Declare a list to hold InstitutionName objects, which will be fetched from the database.
        List<InstitutionName> institutionNameList;

        try {
            // Retrieve all institution names from the database using the DAO.
            institutionNameList = institutionNameDAO.getAll();

            // Set the list of institution names as a request attribute to make it available to the JSP page.
            request.setAttribute("institutionNameList", institutionNameList);
        } catch (SQLException e) {
            // Log any SQL exceptions that occur while fetching the institution names.
            Logger.getLogger(AcademicInstitutionRegistrationServlet.class.getName())
                    .log(Level.SEVERE, "Error fetching institution names", e);
        }
    }
    
     /**
     * Called when the servlet is destroyed (shutting down), closes the database
     * connection pool.
     */
    @Override
    public void destroy() {
        // Close the connection pool to release resources
        DatabaseConnectionUtil.closeDataSource();
    }
 
} //end of class
