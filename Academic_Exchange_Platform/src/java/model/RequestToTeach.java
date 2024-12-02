package model;

/**
 * Represents a request made by an academic professional to teach a specific course.
 * Tracks the request status and notification details.
 */
public class RequestToTeach {

    private int requestToTeachID; // Primary key
    private int professionalID; // Foreign key to AcademicProfessional
    private int courseID; // Foreign key to Course
    private String requestDate;
    private String status;
    private String notificationMessage;
    private String notificationDate;
    
    public RequestToTeach() {
    	
    }

    /**
     * Constructor to create a RequestToTeach object.
     *
     * @param requestToTeachID the unique ID for the request.
     * @param professionalID the ID of the academic professional making the request.
     * @param courseID the ID of the course for which the request is made.
     * @param requestDate the date the request was made.
     * @param status the status of the request (Pending, Accepted, Rejected).
     * @param notificationMessage the message associated with the request status.
     * @param notificationDate the date and time of the notification.
     */
    public RequestToTeach(int requestToTeachID, int professionalID, int courseID, String requestDate, 
                          String status, String notificationMessage, String notificationDate) {
        this.requestToTeachID = requestToTeachID;
        this.professionalID = professionalID;
        this.courseID = courseID;
        this.requestDate = requestDate;
        this.status = status;
        this.notificationMessage = notificationMessage;
        this.notificationDate = notificationDate;
    }

    /**
     * Gets the request ID.
     *
     * @return the request ID.
     */
    public int getRequestToTeachID() {
        return requestToTeachID;
    }

    /**
     * Sets the request ID.
     *
     * @param requestToTeachID the request ID to set.
     */
    public void setRequestToTeachID(int requestToTeachID) {
        this.requestToTeachID = requestToTeachID;
    }

    /**
     * Gets the professional ID.
     *
     * @return the professional ID.
     */
    public int getProfessionalID() {
        return professionalID;
    }

    /**
     * Sets the professional ID.
     *
     * @param professionalID the professional ID to set.
     */
    public void setProfessionalID(int professionalID) {
        this.professionalID = professionalID;
    }

    /**
     * Gets the course ID.
     *
     * @return the course ID.
     */
    public int getCourseID() {
        return courseID;
    }

    /**
     * Sets the course ID.
     *
     * @param courseID the course ID to set.
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    /**
     * Gets the request date.
     *
     * @return the request date.
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * Sets the request date.
     *
     * @param requestDate the request date to set.
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Gets the status of the request.
     *
     * @return the status of the request.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the request.
     *
     * @param status the status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the notification message associated with the request.
     *
     * @return the notification message.
     */
    public String getNotificationMessage() {
        return notificationMessage;
    }

    /**
     * Sets the notification message associated with the request.
     *
     * @param notificationMessage the notification message to set.
     */
    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    /**
     * Gets the notification date.
     *
     * @return the notification date.
     */
    public String getNotificationDate() {
        return notificationDate;
    }

    /**
     * Sets the notification date.
     *
     * @param notificationDate the notification date to set.
     */
    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }
}
