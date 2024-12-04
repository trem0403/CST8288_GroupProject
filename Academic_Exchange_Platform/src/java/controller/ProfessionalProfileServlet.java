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
import model.AcademicProfessional;
import dao.AcademicProfessionalDAO;
import dao.UserDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import model.AcademicProfessional;

/**
 * Servlet implementation class ProfessionalProfileServlet. This servlet is
 * mapped to handle requests for viewing and updating an academic professional's
 * profile.
 *
 * @author Sancheaz + Ethan
 */
@WebServlet("/professionalProfile")
public class ProfessionalProfileServlet extends HttpServlet {

    private AcademicProfessionalDAO academicProfessionalDAO;
    private UserDAO userDAO;

    /**
     * Initializes the servlet and sets up the DAO instance. This method is
     * called once when the servlet is first loaded.
     *
     * @throws ServletException if an initialization error occurs
     */
    @Override
    public void init() throws ServletException {
        academicProfessionalDAO = new AcademicProfessionalDAO();
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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            // Redirect to login page if user is not authenticated
            response.sendRedirect("login");
            return;
        }

        int userID = 0;
        try {
            // Attempt to parse the userID from the session attribute
            userID = (Integer) session.getAttribute("userID");
        } catch (NumberFormatException ex) {
            Logger.getLogger(ProfessionalProfileServlet.class.getName()).log(Level.SEVERE, "Unable to fetch userID", ex);
        }

        try {
            // Fetch the academic professional's profile by userID
            AcademicProfessional academicProfessional = academicProfessionalDAO.getByID(userID);
            request.setAttribute("academicProfessional", academicProfessional);

            // Forward the request to the profile details JSP for rendering
            request.getRequestDispatcher("WEB-INF/views/academic_professional_details.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProfessionalProfileServlet.class.getName()).log(Level.SEVERE, "Error fetching profile", ex);
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
        // Get the existing session without creating a new one (null if no session exists)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            // Redirect to login page if user is not authenticated
            response.sendRedirect("login");
            return;
        }

        int userID = 0;
        try {
            // Attempt to parse the userID from the session attribute
            userID = (Integer) session.getAttribute("userID");
        } catch (NumberFormatException ex) {
            Logger.getLogger(ProfessionalProfileServlet.class.getName()).log(Level.SEVERE, "Unable to fetch userID", ex);
        }

        // Retrieve updated profile data from the form
        String name = request.getParameter("name");
        String currentPositionAtInstitution = request.getParameter("currentPositionAtInstitution");
        String educationBackground = request.getParameter("educationBackground");
        String areaOfExpertise = request.getParameter("areaOfExpertise");

        AcademicProfessional updatedProfile = null;

        try {
            // Fetch the existing profile to update
            updatedProfile = academicProfessionalDAO.getByID(userID);
        } catch (SQLException ex) {
            Logger.getLogger(ProfessionalProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Set updated data on the profile object
        updatedProfile.setName(name);
        updatedProfile.setCurrentPositionAtInstitution(currentPositionAtInstitution);
        updatedProfile.setEducationBackground(educationBackground);
        updatedProfile.setAreaOfExpertise(areaOfExpertise);

        try {
            // Save the updated profile to the database
            academicProfessionalDAO.update(updatedProfile);
            // Forward to a success page after update
            request.getRequestDispatcher("WEB-INF/views/professionalRegisterSuccess.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProfessionalProfileServlet.class.getName()).log(Level.SEVERE, "Error updating profile", ex);

        }
    }
} // end of class
