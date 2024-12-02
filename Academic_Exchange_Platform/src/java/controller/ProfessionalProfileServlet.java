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
 *
 * @author Sancheaz + Ethan
 */
@WebServlet("/professionalProfile")
public class ProfessionalProfileServlet extends HttpServlet {
    private AcademicProfessionalDAO academicProfessionalDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        academicProfessionalDAO = new AcademicProfessionalDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Do not create a new session
        if (session == null || session.getAttribute("userID") == null) {
            response.sendRedirect("WEB-INF/views/login.jsp"); // Redirect to login if not authenticated
            return;
        }

        int userID;
        try {
            userID = Integer.parseInt((String) session.getAttribute("userID"));
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid user ID format", e);
        }

        try {
            AcademicProfessional academicProfessional = academicProfessionalDAO.getByID(userID);
            request.setAttribute("academicProfessional", academicProfessional);
            request.getRequestDispatcher("WEB-INF/views/academic_professional_details.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error fetching profile", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            response.sendRedirect("WEB-INF/views/login.jsp");
            return;
        }
        

        int userID;
        try {
            userID = Integer.parseInt((String) session.getAttribute("userID"));
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid user ID format", e);
        }
        
        
        String name = request.getParameter("name");
        String currentPositionAtInstitution = request.getParameter("currentPositionAtInstitution");
        String educationBackground = request.getParameter("educationBackground");
        String areaOfExpertise = request.getParameter("areaOfExpertise");

        AcademicProfessional updatedProfile = null;
        
       
        try {
            updatedProfile = academicProfessionalDAO.getByID(userID);
        } catch (SQLException ex) {
            Logger.getLogger(ProfessionalProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        updatedProfile.setName(name);
        updatedProfile.setCurrentPositionAtInstitution(currentPositionAtInstitution);
        updatedProfile.setEducationBackground(educationBackground);
        updatedProfile.setAreaOfExpertise(areaOfExpertise);

        try {
            academicProfessionalDAO.update(updatedProfile);
            request.getRequestDispatcher("WEB-INF/views/professionalRegisterSuccess.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error updating profile", e);
        }
    }
}