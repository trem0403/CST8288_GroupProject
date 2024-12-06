/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import model.AcademicInstitution;
import dao.AcademicInstitutionDAO;
import dao.DatabaseConnectionUtil;
import dao.UserDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AcademicProfessional;

/**
 * Servlet implementation class InstitutionProfileServlet. This servlet is
 * mapped to handle requests for viewing and updating an academic professional's
 * profile.
 *
 * @author Sancheaz + Ethan
 */
@WebServlet("/institutionProfile")
public class InstitutionProfileServlet extends HttpServlet {

    private AcademicInstitutionDAO academicInstitutionDAO;
    private UserDAO userDAO;

    /**
     * Initializes the servlet and sets up the DAO instance. This method is
     * called once when the servlet is first loaded.
     *
     * @throws ServletException if an initialization error occurs
     */
    @Override
    public void init() throws ServletException {
        academicInstitutionDAO = new AcademicInstitutionDAO();
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
        // Get the existing session without creating a new one (null if no session exists)
        // Validate session
        ServletUtils.validateSession(request, response);

        // Fetch userID from session
        int userID = ServletUtils.getUserIDFromSession(request);

        try {
            // Fetch the academic professional's profile by userID
            AcademicInstitution academicInstitution = academicInstitutionDAO.getByID(userID);
            request.setAttribute("academicInstitution", academicInstitution);

            // Forward the request to the profile details JSP for rendering
            request.getRequestDispatcher("WEB-INF/views/academic_institution_details.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InstitutionProfileServlet.class.getName()).log(Level.SEVERE, "Error fetching profile", ex);
        }
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
        
        // Validate session
        ServletUtils.validateSession(request, response);

        // Fetch userID from session
        int userID = ServletUtils.getUserIDFromSession(request);
        

        // Retrieve updated profile data from the form
        String zip = request.getParameter("zip");
        

        AcademicInstitution updatedProfile = null;

        try {
            // Fetch the current profile used for the session to update
            updatedProfile = academicInstitutionDAO.getByID(userID);
        } catch (SQLException ex) {
            Logger.getLogger(InstitutionProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set updated data on the profile object
        updatedProfile.setZip(zip);
        
        try {
            // Save the updated profile to the database
            academicInstitutionDAO.update(updatedProfile);
            // Forward to a success page after update
            request.getRequestDispatcher("WEB-INF/views/professionalRegisterSuccess.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InstitutionProfileServlet.class.getName()).log(Level.SEVERE, "Error updating profile", ex);

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
    
} // end of class
