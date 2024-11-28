package model;

import enums.Role;

/**
 * Represents an academic institution. 
 * This class extends the User class since institutions are users in the system.
 */
public class AcademicInstitution extends User {
    private int institutionNameID;
    private String zip;

    /**
     * Constructor for creating an AcademicInstitution object.
     *
     * @param userID           The unique identifier of the user.
     * @param email            The email of the institution.
     * @param password         The password for the institution's account.
     * @param role             The role (AcademicInstitution).
     * @param institutionNameID The identifier of the institution's name.
     * @param zip              The ZIP code where the institution is located.
     */
    public AcademicInstitution(int userID, String email, String password, Role role, int institutionNameID, String zip) {
        super(userID, email, password, role);
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
