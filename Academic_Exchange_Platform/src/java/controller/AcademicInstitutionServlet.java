package controller;

import dao.AcademicInstitutionDAO;
import model.AcademicInstitution;
import dao.InstitutionNameDAO;
import model.InstitutionName;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet("/institutionRegister")
public class AcademicInstitutionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    final private AcademicInstitutionDAO academicInstitutionDAO = new AcademicInstitutionDAO();
    final private InstitutionNameDAO institutionNameDAO = new InstitutionNameDAO();

    public AcademicInstitutionServlet() {
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
//        academicInstitutionDAO = new AcademicInstitutionDAO();
//        institutionNameDAO = new InstitutionNameDAO();
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

        // Declare a list to hold InstitutionName objects, which will be fetched from the database.
        List<InstitutionName> institutionNamesList;

        try {
            // Retrieve all institution names from the database using the DAO.
            institutionNamesList = institutionNameDAO.getAll();

            // Set the list of institution names as a request attribute to make it available to the JSP page.
            request.setAttribute("institutionNamesList", institutionNamesList);
        } catch (SQLException e) {
            // Log any SQL exceptions that occur while fetching the institution names.
            Logger.getLogger(AcademicInstitutionServlet.class.getName())
                    .log(Level.SEVERE, "Error fetching institution names", e);
        }

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

        // Create the AcademicInstitution object
        AcademicInstitution academicInstitution = new AcademicInstitution(email, password, role, institutionNameID);

        // Insert into database
        try {
            academicInstitutionDAO.create(academicInstitution);
            request.setAttribute("message", "Institution successfully registered!");
        } catch (SQLException ex) {
            Logger.getLogger(AcademicInstitutionServlet.class.getName()).log(Level.SEVERE, "Error creating institution", ex);
            request.setAttribute("error", "Failed to register institution. Please try again.");
        }

        // Redirect to a success page or show confirmation
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/academic_institution_details.jsp");
        dispatcher.forward(request, response);
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AcademicInstitutionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AcademicInstitutionServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

} //end of class
