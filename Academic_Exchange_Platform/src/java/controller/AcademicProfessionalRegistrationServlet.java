package controller;

import dao.DatabaseConnectionUtil;

import dao.AcademicProfessionalDAO;
import model.AcademicProfessional;

import dao.AcademicInstitutionDAO;

import dao.InstitutionNameDAO;
import model.InstitutionName;

import dao.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Ethan Tremblay
 */
@WebServlet(name = "AcademicProfessionalRegistrationServlet", urlPatterns = "/professionalRegister")
public class AcademicProfessionalRegistrationServlet extends HttpServlet {

    private AcademicProfessionalDAO academicProfessionalDAO;
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
        academicProfessionalDAO = new AcademicProfessionalDAO();
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
        request.getRequestDispatcher("WEB-INF/views/academic_professional_registration.jsp").forward(request, response);
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
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int institutionNameID = Integer.parseInt(request.getParameter("institutionNameID"));
        String academicPosition = request.getParameter("academicPosition");

        // Fetch the institutionID based on the selected institutionNameID
        int institutionID = -1;

        // Define role explicitly
        String role = "AcademicProfessional";

        // Initialize and declare error messages
        String nameError = ValidationUtils.nameValidation(name);
        String emailError = ValidationUtils.emailValidation(email);
        String passwordError = ValidationUtils.passwordValidation(password);
        String academicPositionError = ValidationUtils.academicPositionValidation(academicPosition);
        String institutionIDError = null;

        // Fetch the institutionID based on the selected institutionNameID
        try {
            institutionID = academicInstitutionDAO.getInstitutionIDByNameID(institutionNameID);
            if (institutionID == -1) {
                institutionIDError = "No Academic Institution account is linked to this institution.";
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicProfessionalRegistrationServlet.class.getName())
                    .log(Level.SEVERE, "Error fetching institution ID", ex);
            institutionIDError = "An error occurred while fetching the institution ID.";
        }

        // If there are errors, set them as request attributes and forward back to the JSP
        if (nameError != null || emailError != null || passwordError != null || academicPositionError != null || institutionIDError != null) {
            request.setAttribute("name-error", nameError);
            request.setAttribute("email-error", emailError);
            request.setAttribute("password-error", passwordError);
            request.setAttribute("academicPosition-error", academicPositionError);
            request.setAttribute("institutionID-error", institutionIDError);

            // Add form data to preserve user input
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("academicPosition", academicPosition);

            sendInstitutionNameList(request, response);

            // Forward to the registration page again
            request.getRequestDispatcher("WEB-INF/views/academic_professional_registration.jsp").forward(request, response);
            return;
        } else {
            // Continue with the registration process (e.g., save the data to the database)
            // If successful, redirect to a success page

            // Create the AcademicInstitution object
            AcademicProfessional academicProfessional = new AcademicProfessional(email, password, role, name, institutionID, academicPosition);

            // Insert into database
            ServletUtils.insertUser(academicProfessional);

            // Fetch userID for session handling
            int userID = ServletUtils.getUserID(email, password);

            /*
            * After successful registration and fetching userID,
            * Store useriD and role into a session.
             */
            ServletUtils.storeUserInSession(request, userID, role);

            // Redirect to profile setup
            response.sendRedirect("professionalProfile");
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
            Logger.getLogger(AcademicProfessionalRegistrationServlet.class.getName())
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
