package model;

import enums.Role;

/**
 * Represents an academic institution. 
 * This class extends the User class since institutions are users in the system.
 */
public class AcademicInstitution extends User {
    private int institutionNameID; // Foreign key to InstitutionNames
    private String zip;
    
    /**
     * Default Constructor for creating an AcademicInstitution object.
     * @param userID            The userID of the institution.
     * @param email             The email of the institution.
     * @param password          The password for the institution's account.
     * @param role              The role (AcademicInstitution).
     * @param institutionNameID The identifier of the institution's name.
     * @param zip               The zip code of the institution's address. 
     */
    public AcademicInstitution(int userID, String email, String password, String role, int institutionNameID, String zip) {
        super(email, password, role);
        this.institutionNameID = institutionNameID;
        this.zip = zip;
    }

    /**
     * Constructor for creating an AcademicInstitution object during registration.
     * 
     * @param email             The email of the institution.
     * @param password          The password for the institution's account.
     * @param role              The role (AcademicInstitution).
     * @param institutionNameID The identifier of the institution's name.
     */
    public AcademicInstitution(String email, String password, String role, int institutionNameID) {
        super(email, password, role);
        this.institutionNameID = institutionNameID;
    }
    
    /**
     * Constructor for creating an AcademicInstitution object when fetching all AcademicInstitution users.
     * @param userID            The userID of the institution.
     * @param institutionNameID The identifier of the institution's name.
     * @param zip               The zip code of the institution's address. 
     */
    public AcademicInstitution(int userID, int institutionNameID, String zip) {
        super(userID);
        this.institutionNameID = institutionNameID;
        this.zip = zip;
    }
    
    

    /** @return The institution's name identifier. */
    public int getInstitutionNameID() {
        return institutionNameID;
    }

    /** @param institutionNameID The institution's name identifier to set. */
    public void setInstitutionNameID(int institutionNameID) {
        this.institutionNameID = institutionNameID;
    }

    /** @return The ZIP code of the institution. */
    public String getZip() {
        return zip;
    }

    /** @param zip The ZIP code to set for the institution. */
    public void setZip(String zip) {
        this.zip = zip;
    }
}
