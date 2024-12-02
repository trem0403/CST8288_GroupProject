/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AcademicProfessionalDAO;
import model.AcademicProfessional;

import dao.AcademicInstitutionDAO;
import model.AcademicInstitution;

import dao.UserDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ripperz KOF
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private AcademicProfessionalDAO academicProfessionalDAO;
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
        // Initialize the DAO's
        academicProfessionalDAO = new AcademicProfessionalDAO();
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

        // Forward the request and response to the JSP page for rendering the login form.
        request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
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

        String role = null;

        // Initialize error messages
        String loginError = null;

        boolean hasError = false;

        // Authenticate login information
        try {
            if (!userDAO.authenticate(email, password)) {
                loginError = "Email or password is not correct, please try again....";
                hasError = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, "Error with query", ex);
            loginError = "An error occurred while authenticating. Please try again.";
            hasError = true;
        }

        // If there are errors, set them as request attributes and forward back to the JSP
        if (hasError) {
            request.setAttribute("login-error", loginError);

            // Add form data to preserve user input
            request.setAttribute("email", email);
            request.setAttribute("password", password);

            // Forward to the registration page again
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
            return;
        } else {
            try {
                // Continue with the login process (e.g., Go to home page)

                // Fetch user role
                role = userDAO.getRole(email, password);
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, "Error fetching user role", ex);
                request.setAttribute("error", "Failed to register professional. Please try again.");
            }

            switch (role) {
                case "AcademicProfessional":
                    response.sendRedirect("professionalProfile");
                    break;
                case "AcademicInstitution":
                    response.sendRedirect("InstitutionProfile");
                    break;
                default:
                    break;
            } // end of switch-case
        }
    }
} // end of class
