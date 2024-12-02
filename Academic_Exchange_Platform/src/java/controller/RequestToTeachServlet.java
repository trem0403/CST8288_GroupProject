/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RequestToTeachDAO;
import model.RequestToTeach;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Micheal Acquah
 */
@WebServlet(name = "RequestToTeachServlet", urlPatterns = "/RequestToTeachServlet")
public class RequestToTeachServlet extends HttpServlet {
    
        private RequestToTeachDAO requestToTeachDAO;
        
        
     /**
     * Initializes the servlet and sets up the DAO instance. This method is
     * called once when the servlet is first loaded.
     *
     * @throws ServletException if an initialization error occurs
     */
    @Override
    public void init() throws ServletException {
        // Initialize the DAO's
        requestToTeachDAO = new RequestToTeachDAO();
    }


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

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

}
