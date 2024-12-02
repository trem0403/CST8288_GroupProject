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
     * Retrieves a RequestToTeach entry by its ID.
     *
     * @param requestToTeachID The ID of the request to retrieve.
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
     * Retrieves all RequestToTeach entries for a specific academic professional.
     *
     * @param professionalID The ID of the academic professional whose requests to fetch.
     * @return A list of RequestToTeach objects for the specified academic professional.
     * @throws SQLException If a database access error occurs.
     */
    public List<RequestToTeach> getAll() throws SQLException {
        List<RequestToTeach> requests = new ArrayList<>();
        String selectSQL = "SELECT * FROM RequestToTeach WHERE professionalID = ? ORDER BY requestDate DESC";

        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSQL)) {

            
            try(ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                requests.add(new RequestToTeach(
                    rs.getInt("requestToTeachID"),
                    rs.getInt("professionalID"),
                    rs.getInt("courseID"),
                    rs.getString("requestDate"),
                    rs.getString("status"),
                    rs.getString("notificationMessage"),
                    rs.getString("notificationDate")
                ));
            }
        }
        }
        return requests;
    }
	
	/**
     * Updates the status of a teaching request and sends a notification.
     *
     * @param requestID          The ID of the request to update.
     * @param status            The new status of the request (e.g., "Accepted", "Rejected").
     * @param notificationMessage The message to send as part of the notification.
     * @throws SQLException If a database access error occurs.
     */
    public void update(RequestToTeach requestToTeach) throws SQLException {
        String updateSQL = "UPDATE RequestToTeach SET status = ?, notificationMessage = ?, notificationDate = NOW() WHERE requestToTeachID = ?";
        
        try (Connection conn = DatabaseConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

            stmt.setString(1, requestToTeach.getStatus());
            stmt.setString(2, requestToTeach.getNotificationMessage());
            stmt.setInt(3, requestToTeach.getRequestToTeachID());
            stmt.executeUpdate();
        }
    }
	
	
	

}
