package controller;

import dao.RequestToTeachDAO;
import dao.CourseDAO;
import model.RequestToTeach;
import model.Course;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/institution/requests")
public class InstitutionRequestServlet extends HttpServlet {

    private RequestToTeachDAO requestToTeachDAO;
    private CourseDAO courseDAO;

    @Override
    public void init() {
        requestToTeachDAO = new RequestToTeachDAO();
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ensure the institution is logged in
        if (!ServletUtils.validateSession(request, response)) {
            return; // If session validation fails, the user will be redirected, and we won't proceed further
        }

        // Fetch institutionID from session
        int institutionID = ServletUtils.getUserIDFromSession(request);

        try {
            List<RequestToTeach> requests = requestToTeachDAO.getAll();

            // Filter requests for the specific institution
            requests.removeIf(r -> {
                try {
                    Course course = courseDAO.getByID(r.getCourseID());
                    return course == null || course.getInstitutionID() != institutionID;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return true;
                }
            });

            request.setAttribute("requests", requests);
            request.getRequestDispatcher("/WEB-INF/views/institutionRequests.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(InstitutionRequestServlet.class.getName()).log(Level.SEVERE, "Error fetching requests", ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int requestID = Integer.parseInt(request.getParameter("requestID"));
        String status = request.getParameter("status");
        String notificationMessage = "Your request to teach has been " + status + " by the institution.";

        try {
            requestToTeachDAO.updateRequestStatus(requestID, status, notificationMessage);
        } catch (SQLException ex) {
            Logger.getLogger(InstitutionRequestServlet.class.getName()).log(Level.SEVERE, "Error updating request status", ex);
            throw new ServletException(ex);
        }

        response.sendRedirect("requests?institutionID=" + ServletUtils.getUserIDFromSession(request));
    }
}