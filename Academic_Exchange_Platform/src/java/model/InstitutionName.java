package model;

/**
 * Represents an institution name entity.
 * Each institution is identified by a unique name.
 */
public class InstitutionName {

    private int institutionNameID; // Primary key
    private String name;

    /**
     * Constructor to create an InstitutionNames object.
     *
     * @param institutionNameID the unique ID of the institution name.
     * @param name the name of the institution.
     */
    public InstitutionName(int institutionNameID, String name) {
        this.institutionNameID = institutionNameID;
        this.name = name;
    }

    /**
     * Gets the institution name ID.
     *
     * @return the institution name ID.
     */
    public int getInstitutionNameID() {
        return institutionNameID;
    }

    /**
     * Sets the institution name ID.
     *
     * @param institutionNamesID the institution name ID to set.
     */
    public void setInstitutionNameID(int institutionNamesID) {
        this.institutionNameID = institutionNamesID;
    }

    /**
     * Gets the name of the institution.
     *
     * @return the name of the institution.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the institution.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
