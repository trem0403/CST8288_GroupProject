package model;

/**
 * Represents an academic professional in the system.
 * This class extends the User class since academic professionals are also users.
 */
public class AcademicProfessional extends User {
    private String name;
    private int institutionID;
    private String academicPosition;
    private String educationBackground;
    private String areaOfExpertise;

    /**
     * Default constructor for creating an AcademicProfessional object.
     * 
     * @param userID              The user ID of the academic professional.
     * @param email               The email of the academic professional.
     * @param password            The password for the academic professional's account.
     * @param role                The role (AcademicProfessional).
     * @param name                The name of the academic professional.
     * @param institutionID       The ID of the associated academic institution.
     * @param academicPosition    The position/title of the academic professional.
     * @param educationBackground The education background of the academic professional.
     * @param areaOfExpertise     The area of expertise of the academic professional.
     */
    public AcademicProfessional(int userID, String email, String password, String role, String name, int institutionID, String academicPosition, String educationBackground, String areaOfExpertise) {
        super(userID, email, password, role);
        this.name = name;
        this.institutionID = institutionID;
        this.academicPosition = academicPosition;
        this.educationBackground = educationBackground;
        this.areaOfExpertise = areaOfExpertise;
    }

    /**
     * Constructor for creating an AcademicProfessional object during registration.
     * 
     * @param email               The email of the academic professional.
     * @param password            The password for the academic professional's account.
     * @param role                The role (AcademicProfessional).
     * @param name                The name of the academic professional.
     * @param institutionID       The ID of the associated academic institution.
     * @param academicPosition    The position/title of the academic professional.
     */
    public AcademicProfessional(String email, String password, String role, String name, int institutionID, String academicPosition) {
        super(email, password, role);
        this.name = name;
        this.institutionID = institutionID;
        this.academicPosition = academicPosition;
    }
    
    /**
     * Constructor for creating an AcademicProfessional object when fetching all AcademicProfessional users.
     * 
     * @param userID              The user ID of the academic professional.
     * @param name                The name of the academic professional.
     * @param institutionID       The ID of the associated academic institution.
     * @param academicPosition    The position/title of the academic professional.
     * @param educationBackground The education background of the academic professional.
     * @param areaOfExpertise     The area of expertise of the academic professional.
     */
    public AcademicProfessional(int userID, String name, int institutionID, String academicPosition, String educationBackground, String areaOfExpertise) {
        super(userID);
        this.name = name;
        this.institutionID = institutionID;
        this.academicPosition = academicPosition;
        this.educationBackground = educationBackground;
        this.areaOfExpertise = areaOfExpertise;
    }
    
    

    /** @return The name of the academic professional. */
    public String getName() {
        return name;
    }

    /** @param name The name to set for the academic professional. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return The ID of the associated academic institution. */
    public int getInstitutionID() {
        return institutionID;
    }

    /** @param institutionID The institution ID to set. */
    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }

    /** @return The position/title of the academic professional. */
    public String getAcademicPosition() {
        return academicPosition;
    }

    /** @param academicPosition The position to set for the academic professional. */
    public void setAcademicPosition(String academicPosition) {
        this.academicPosition = academicPosition;
    }

    /** @return The education background of the academic professional. */
    public String getEducationBackground() {
        return educationBackground;
    }

    /** @param educationBackground The education background to set. */
    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    /** @return The area of expertise of the academic professional. */
    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    /** @param areaOfExpertise The area of expertise to set. */
    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }
}
