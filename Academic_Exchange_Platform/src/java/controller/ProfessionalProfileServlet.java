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
import dao.ProfessionalProfileDAO;
/**
 *
 * @author Sancheaz
 */
@WebServlet("/profile")
public class ProfessionalProfileServlet extends HttpServlet {
    private ProfessionalProfileDAO profileDAO;

    @Override
    public void init() {
        profileDAO = new ProfessionalProfileDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String name = request.getParameter("name");
        int institutionID = Integer.parseInt(request.getParameter("institutionID"));
        String academicPosition = request.getParameter("academicPosition");
        String currentPositionAtInstitution = request.getParameter("currentPositionAtInstitution");
        String educationBackground = request.getParameter("educationBackground");
        String areaOfExpertise = request.getParameter("areaOfExpertise");

        // Create AcademicProfessional object
        AcademicProfessional newProfile = new AcademicProfessional(name, institutionID, academicPosition, currentPositionAtInstitution, educationBackground, areaOfExpertise);

        try {
            // Insert the profile into the database
            profileDAO.insertProfile(newProfile);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error inserting profile", e); // Use throw new ServletException to properly handle errors
        }

        // Redirect to success page
        response.sendRedirect("professionalRegisterSuccess.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward GET requests to another method or handle them similarly
        response.sendRedirect("professionalRegister.jsp"); // 
    }
}