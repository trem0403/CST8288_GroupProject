package model;

/**
 * Represents a course offered by an academic institution.
 */
public class Course {

    private int courseID; // Primary key
    private int institutionID; // Foreign key to AcademicInstitution
    private String title;
    private String code;
    private int termID; // Foreign key to Term
    private String outline;
    private String schedule;
    private String deliveryMethod;
    private String preferredQualifications;
    private double compensation;

    /**
     * Default Constructor for a Course object.
     *
     * @param courseID the unique ID of the course.
     * @param institutionID the ID of the institution offering the course.
     * @param title the title of the course.
     * @param code the course code.
     * @param termID the ID of the term the course belongs to.
     * @param outline the outline of the course.
     * @param schedule the schedule of the course (Morning, Afternoon, Evening).
     * @param deliveryMethod the delivery method of the course (In-Person, Remote, Hybrid).
     * @param preferredQualifications the qualifications preferred for the course.
     * @param compensation the compensation for the course.
     */
    public Course(int courseID, int institutionID, String title, String code, int termID, String outline, 
                  String schedule, String deliveryMethod, String preferredQualifications, double compensation) {
        this.courseID = courseID;
        this.institutionID = institutionID;
        this.title = title;
        this.code = code;
        this.termID = termID;
        this.outline = outline;
        this.schedule = schedule;
        this.deliveryMethod = deliveryMethod;
        this.preferredQualifications = preferredQualifications;
        this.compensation = compensation;
    }
    
    /**
     * Constructor to create a Course object as an Academic Institution.
     *
     * @param institutionID the ID of the institution offering the course.
     * @param title the title of the course.
     * @param code the course code.
     * @param termID the ID of the term the course belongs to.
     * @param outline the outline of the course.
     * @param schedule the schedule of the course (Morning, Afternoon, Evening).
     * @param deliveryMethod the delivery method of the course (In-Person, Remote, Hybrid).
     * @param preferredQualifications the qualifications preferred for the course.
     * @param compensation the compensation for the course.
     */
    public Course(int institutionID, String title, String code, int termID, String outline, 
                  String schedule, String deliveryMethod, String preferredQualifications, double compensation) {
        this.institutionID = institutionID;
        this.title = title;
        this.code = code;
        this.termID = termID;
        this.outline = outline;
        this.schedule = schedule;
        this.deliveryMethod = deliveryMethod;
        this.preferredQualifications = preferredQualifications;
        this.compensation = compensation;
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
     * Gets the institution ID.
     *
     * @return the institution ID.
     */
    public int getInstitutionID() {
        return institutionID;
    }

    /**
     * Sets the institution ID.
     *
     * @param institutionID the institution ID to set.
     */
    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }

    /**
     * Gets the title of the course.
     *
     * @return the title of the course.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the course.
     *
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the course code.
     *
     * @return the course code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the course code.
     *
     * @param code the course code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the term ID associated with the course.
     *
     * @return the term ID.
     */
    public int getTermID() {
        return termID;
    }

    /**
     * Sets the term ID associated with the course.
     *
     * @param termID the term ID to set.
     */
    public void setTermID(int termID) {
        this.termID = termID;
    }

    /**
     * Gets the outline of the course.
     *
     * @return the outline of the course.
     */
    public String getOutline() {
        return outline;
    }

    /**
     * Sets the outline of the course.
     *
     * @param outline the outline to set.
     */
    public void setOutline(String outline) {
        this.outline = outline;
    }

    /**
     * Gets the schedule of the course.
     *
     * @return the schedule (Morning, Afternoon, Evening).
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * Sets the schedule of the course.
     *
     * @param schedule the schedule to set.
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /**
     * Gets the delivery method of the course.
     *
     * @return the delivery method (In-Person, Remote, Hybrid).
     */
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Sets the delivery method of the course.
     *
     * @param deliveryMethod the delivery method to set.
     */
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    /**
     * Gets the preferred qualifications for the course.
     *
     * @return the preferred qualifications.
     */
    public String getPreferredQualifications() {
        return preferredQualifications;
    }

    /**
     * Sets the preferred qualifications for the course.
     *
     * @param preferredQualifications the qualifications to set.
     */
    public void setPreferredQualifications(String preferredQualifications) {
        this.preferredQualifications = preferredQualifications;
    }

    /**
     * Gets the compensation for the course.
     *
     * @return the compensation amount.
     */
    public double getCompensation() {
        return compensation;
    }

    /**
     * Sets the compensation for the course.
     *
     * @param compensation the compensation amount to set.
     */
    public void setCompensation(double compensation) {
        this.compensation = compensation;
    }
}
