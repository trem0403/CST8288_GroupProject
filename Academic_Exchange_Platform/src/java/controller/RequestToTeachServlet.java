/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RequestToTeachDAO;
import model.Course;
import model.RequestToTeach;
import dao.CourseDAO;
import dao.DatabaseConnectionUtil;
import dao.InstitutionNameDAO;
import dao.TermDAO;

import java.util.Map;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.InstitutionName;
import model.Term;

/**
 *
 * @author Micheal Acquah + Ethan Tremblay
 */
@WebServlet(name = "RequestToTeachServlet", urlPatterns = "/RequestToTeachServlet")
public class RequestToTeachServlet extends HttpServlet {

    private RequestToTeachDAO requestToTeachDAO;
    private CourseDAO courseDAO;
    private InstitutionNameDAO institutionNameDAO;
    private TermDAO termDAO;

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
        courseDAO = new CourseDAO();
        institutionNameDAO = new InstitutionNameDAO();
        termDAO = new TermDAO();
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

        try {
            // Get all courses
            List<Course> courses = courseDAO.getAll();
            request.setAttribute("courses", courses);

            // Get all terms
            List<Term> terms = termDAO.getAll();
            request.setAttribute("terms", terms);

            // Get all institutions
            List<InstitutionName> institutions = institutionNameDAO.getAll();
            request.setAttribute("institutions", institutions);

            // Create mappings for institution and term names
            Map<Integer, String> institutionMap = institutions.stream()
                    .collect(Collectors.toMap(InstitutionName::getInstitutionNameID, InstitutionName::getName));
            request.setAttribute("institutionMap", institutionMap);

            Map<Integer, String> termMap = terms.stream()
                    .collect(Collectors.toMap(Term::getTermID, Term::getTerm));
            request.setAttribute("termMap", termMap);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/course_search.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Error loading data. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/course_search.jsp");
            dispatcher.forward(request, response);
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
        
        String action = request.getParameter("action");

        if ("requestToTeach".equals(action)) {
            requestToTeach(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
        }

    }

    //accept the teaching request
    protected void acceptRequest(HttpServletRequest request, HttpServletResponse response)
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
    protected void rejectRequest(HttpServletRequest request, HttpServletResponse response)
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

    // search for a course
    protected void searchCourses(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String code = request.getParameter("code");

        // Validate termID
        String termIDParam = request.getParameter("termID");
        int termID = 0;
        if (termIDParam != null && !termIDParam.isEmpty()) {
            termID = Integer.parseInt(termIDParam);
        } else {
            request.setAttribute("error", "Term is required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/course_search.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Validate institutionID
        String institutionIDParam = request.getParameter("institutionID");
        int institutionID = 0;
        if (institutionIDParam != null && !institutionIDParam.isEmpty()) {
            institutionID = Integer.parseInt(institutionIDParam);
        } else {
            request.setAttribute("error", "Institution is required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/course_search.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            List<Course> courses = courseDAO.searchCourses(title, code, termID, institutionID);
            request.setAttribute("courses", courses);
            request.setAttribute("title", title);
            request.setAttribute("code", code);
            request.setAttribute("termID", termID);
            request.setAttribute("institutionID", institutionID);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/course_search_results.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Error retrieving courses. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/course_search.jsp");
            dispatcher.forward(request, response);
        }
    }

    //create new request to teach objects to handle new requests to teach
    protected void requestToTeach(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        int professionalID = Integer.parseInt(request.getSession().getAttribute("professionalID").toString()); // Assuming logged-in user info
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Format the date as YYYY-MM-DD
        String requestDate = currentDate.toString();

        try {
            RequestToTeach requestToTeach = new RequestToTeach(0, professionalID, courseID, requestDate, "Pending", null, null);

            requestToTeachDAO.create(requestToTeach);

            request.setAttribute("message", "Request to teach submitted successfully!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/course_search_results.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Failed to submit request. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/course_search_results.jsp");
            dispatcher.forward(request, response);
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

}
