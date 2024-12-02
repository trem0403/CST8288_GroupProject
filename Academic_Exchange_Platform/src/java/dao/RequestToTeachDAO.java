package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.RequestToTeach;

public class RequestToTeachDAO implements GenericDAO<RequestToTeach>{
	
	@Override
	public void create(RequestToTeach requestToTeach) throws SQLException {
		
	        String insertSQL = "INSERT INTO RequestToTeach (professionalID, courseID, requestDate, status, notificationMessage, notificationDate) " +
	                           "VALUES (?, ?, NOW(), ?, ?, ?)";

	        try (Connection conn = DatabaseConnectionUtil.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

	            stmt.setInt(1, requestToTeach.getProfessionalID());
	            stmt.setInt(2, requestToTeach.getCourseID());
	            stmt.setString(3, requestToTeach.getStatus());
	            stmt.setString(4, requestToTeach.getNotificationMessage());
	            stmt.setString(5, requestToTeach.getNotificationDate());

	            stmt.executeUpdate();

	            // Retrieve generated request ID
	            ResultSet generatedKeys = stmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                requestToTeach.setRequestToTeachID(generatedKeys.getInt(1));
	            }
	        }
	    }
	
	/**
     * Updates the status of a teaching request and sends a notification.
     *
     * @param requestID          The ID of the request to update.
     * @param status            The new status of the request (e.g., "Accepted", "Rejected").
     * @param notificationMessage The message to send as part of the notification.
     * @throws SQLException If a database access error occurs.
     */
    public void updateRequestStatus(int requestID, String status, String notificationMessage) throws SQLException {
        String updateSQL = "UPDATE RequestToTeach SET status = ?, notificationMessage = ?, notificationDate = NOW() WHERE requestToTeachID = ?";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, status);
            stmt.setString(2, notificationMessage);
            stmt.setInt(3, requestID);
            stmt.executeUpdate();
        }
    }
	
	
	

}
