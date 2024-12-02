package controller;

import dao.DatabaseConnectionUtil;
import dao.AcademicInstitutionDAO;
import model.AcademicInstitution;
import dao.InstitutionNameDAO;
import dao.RequestToTeachDAO;
import model.InstitutionName;
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

/**
 *
 * @author Ethan Tremblay
 */
@WebServlet(name = "AcademicInstitutionServlet", urlPatterns = "/institutionRegister")
public class AcademicInstitutionRegistrationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private AcademicInstitutionDAO academicInstitutionDAO;
    private InstitutionNameDAO institutionNameDAO;
    private RequestToTeachDAO requestToTeachDAO;

    public AcademicInstitutionRegistrationServlet() {
        super();
    }

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
        requestToTeachDAO = new RequestToTeachDAO();
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/academic_institution_registration.jsp");
        dispatcher.forward(request, response);
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

        // Initialize error messages
        String emailError = null;
        String passwordError = null;
        String institutionError = null;

        boolean hasError = false;

        // Check if email format is valid
        if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            emailError = "Invalid email format";
            hasError = true;
        }

        // Check if email is already registered
        try {
            if (academicInstitutionDAO.isEmailAlreadyRegistered(email)) {
                emailError = "The email is already registered. Please use a different email.";
                hasError = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicInstitutionRegistrationServlet.class.getName()).log(Level.SEVERE, "Error with query", ex);
            emailError = "An error occurred while checking the email. Please try again.";
            hasError = true;
        }

        // Check if password length is valid
        if (password.length() < 6) {
            passwordError = "Password must be at least 6 characters";
            hasError = true;
        }
     
        // Check if the institution is already registered
        try {
            if (academicInstitutionDAO.isInstitutionNameAlreadyRegistered(institutionNameID)) {
                institutionError = "An institution with this name is already registered.";
                hasError = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AcademicInstitutionRegistrationServlet.class.getName()).log(Level.SEVERE, "Error checking institution", ex);
            institutionError = "An error occurred while checking the institution. Please try again.";
            hasError = true;
        }

        // If there are errors, set them as request attributes and forward back to the JSP
        if (hasError) {
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
            try {
                academicInstitutionDAO.create(academicInstitution);
                request.setAttribute("message", "Institution successfully registered!");
            } catch (SQLException ex) {
                Logger.getLogger(AcademicInstitutionRegistrationServlet.class.getName()).log(Level.SEVERE, "Error creating institution", ex);
                request.setAttribute("error", "Failed to register institution. Please try again.");
            }
            // Forward to a success page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/academic_institution_details.jsp");
            dispatcher.forward(request, response);
        }
    }
    //accept the teaching request
    private void acceptRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int requestID = Integer.parseInt(request.getParameter("requestID"));
        String institutionName = request.getParameter("institutionName");

        try {
            // Update the request status to 'Accepted'
            requestToTeachDAO.updateRequestStatus(requestID, "Accepted", "Your request has been accepted by " + institutionName);

            // Redirect or forward to appropriate page after accepting the request
            request.setAttribute("message", "The request has been accepted successfully!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/academic_institution_dashboard.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Failed to accept the request. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/academic_institution_dashboard.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    //reject the teaching request
    private void rejectRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int requestID = Integer.parseInt(request.getParameter("requestID"));
        String institutionName = request.getParameter("institutionName");

        try {
            // Update the request status to 'Rejected'
            requestToTeachDAO.updateRequestStatus(requestID, "Rejected", "Your request has been rejected by " + institutionName);

            // Redirect or forward to appropriate page after rejecting the request
            request.setAttribute("message", "The request has been rejected.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/academic_institution_dashboard.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Failed to reject the request. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/academic_institution_dashboard.jsp");
            dispatcher.forward(request, response);
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
     * Called when the servlet is destroyed (shutting down), closes the database connection pool.
     */
    @Override
    public void destroy() {
        // Close the connection pool to release resources
        DatabaseConnectionUtil.closeDataSource();
        }
} //end of class
