package controller;

import dao.RequestToTeachDAO;
import model.RequestToTeach;

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

@WebServlet("/professional/notifications")
public class ProfessionalNotificationServlet extends HttpServlet {

    private RequestToTeachDAO requestToTeachDAO;

    @Override
    public void init() {
        requestToTeachDAO = new RequestToTeachDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletUtils.validateSession(request, response)) {
            return; // If session validation fails, the user will be redirected, and we won't proceed further
        }

        int professionalID = ServletUtils.getUserIDFromSession(request);

        try {
            List<RequestToTeach> requests = requestToTeachDAO.getAll();
            // Filter requests for the specific professional
            requests.removeIf(r -> r.getProfessionalID() != professionalID);

            request.setAttribute("notifications", requests);
            request.getRequestDispatcher("/WEB-INF/views/professionalNotifications.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ProfessionalNotificationServlet.class.getName()).log(Level.SEVERE, "Error fetching notifications", ex);
            throw new ServletException(ex);
        }
    }
}