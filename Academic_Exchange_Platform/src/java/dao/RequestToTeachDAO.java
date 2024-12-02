package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.GenericDAO;
import java.sql.Timestamp;
import java.sql.Date;
import model.RequestToTeach;

public class RequestToTeachDAO implements GenericDAO<RequestToTeach> {

    @Override
    public void create(RequestToTeach requestToTeach) throws SQLException {

        String insertSQL = "INSERT INTO RequestToTeach (professionalID, courseID, requestDate, status, notificationMessage, notificationDate) "
                + "VALUES (?, ?, NOW(), ?, ?, ?)";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, requestToTeach.getProfessionalID());
            stmt.setInt(2, requestToTeach.getCourseID());
//            stmt.setString(3, requestToTeach.getRequestDate());
            stmt.setString(4, requestToTeach.getStatus());
            stmt.setString(5, requestToTeach.getNotificationMessage());
            stmt.setString(6, requestToTeach.getNotificationDate());

            stmt.executeUpdate();

            // Retrieve generated request ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                requestToTeach.setRequestToTeachID(generatedKeys.getInt(1));
            }
        }
    }
    
    /**
     * Retrieves an InstitutionName entry by its ID.
     *
     * @param requestToTeachID The ID of the RequestToTeach entity to retrieve.
     * @return The RequestToTeach object, or null if not found.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public RequestToTeach getByID(int requestToTeachID) throws SQLException {
        String selectSQL = "SELECT * FROM RequestToTeach WHERE requestToTeachID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            stmt.setInt(1, requestToTeachID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new RequestToTeach(
                        rs.getInt("requestToTeachID"),
                        rs.getInt("professionalID"),
                        rs.getInt("courseID"),
                        rs.getString("requestDate"),
                        rs.getString("status"),
                        rs.getString("notificationMessage"),
                        rs.getString("notificationDate")
                );
            }
            return null;
        }
    }
    
    /**
     * Retrieves all RequestToTeach entries from the database.
     *
     * @return A list of RequestToTeach objects.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public List<RequestToTeach> getAll() throws SQLException {
        List<RequestToTeach> RequestToTeachList = new ArrayList<>();
        String selectAllSQL = "SELECT requestToTeachID, professionalID, courseID, requestDate, status, notificationMessage, notificationDate " +
                              "FROM RequestToTeach ORDER BY requestToTeachID";
        
        try (
            Connection conn = DatabaseConnectionUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(selectAllSQL)) {

      
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int requestToTeachID = rs.getInt("requestToTeachID");
                    int professionalID = rs.getInt("professionalID");
                    int courseID = rs.getInt("courseID");
                    String requestDate = rs.getString("requestDate");
                    String status = rs.getString("status");
                    String notificationMessage = rs.getString("notificationMessage");
                    String notificationDate = rs.getString("notificationDate");

                    RequestToTeachList.add(new RequestToTeach(requestToTeachID, professionalID, courseID, requestDate, status, notificationMessage, notificationDate));
                }
            }
        }
        return RequestToTeachList;
    }

    /**
     * Updates the status of a teaching request and sends a notification.
     *
     * @param RequestToTeach
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public void update(RequestToTeach requestToTeach) throws SQLException {
        String updateSQL = "UPDATE RequestToTeach SET professionalID = ?, courseID = ?, requestDate = ?, status = ?, notificationMessage = ?, notificationDate = ? WHERE requestToTeachID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setInt(1, requestToTeach.getProfessionalID());
            stmt.setInt(2, requestToTeach.getCourseID());

            // Converting to java.sql.Date for requestDate (YYYY-MM-DD)
            stmt.setDate(3, Date.valueOf(requestToTeach.getRequestDate()));

            stmt.setString(4, requestToTeach.getStatus());
            stmt.setString(5, requestToTeach.getNotificationMessage());

            // Converting to java.sql.Timestamp for notificationDate (YYYY-MM-DD HH:MM:SS)
            stmt.setTimestamp(6, Timestamp.valueOf(requestToTeach.getNotificationDate()));

            stmt.setInt(7, requestToTeach.getRequestToTeachID());

            stmt.executeUpdate();             
        }
    }

    /**
     * Updates the status of a teaching request and sends a notification.
     *
     * @param requestID The ID of the request to update.
     * @param status The new status of the request (e.g., "Accepted",
     * "Rejected").
     * @param notificationMessage The message to send as part of the
     * notification.
     * @throws SQLException If a database access error occurs.
     */
    public void updateRequestStatus(int requestID, String status, String notificationMessage) throws SQLException {
        String updateSQL = "UPDATE RequestToTeach SET status = ?, notificationMessage = ?, notificationDate = NOW() WHERE requestToTeachID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, status);
            stmt.setString(2, notificationMessage);
            stmt.setInt(3, requestID);
            stmt.executeUpdate();
        }
    }

} // end of class 
