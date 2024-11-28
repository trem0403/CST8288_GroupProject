package model;

/**
 * Represents an institution name entity.
 * Each institution is identified by a unique name.
 */
public class InstitutionNames {

    private int institutionNamesID; // Primary key
    private String name;

    /**
     * Constructor to create an InstitutionNames object.
     *
     * @param institutionNamesID the unique ID of the institution name.
     * @param name the name of the institution.
     */
    public InstitutionNames(int institutionNamesID, String name) {
        this.institutionNamesID = institutionNamesID;
        this.name = name;
    }

    /**
     * Gets the institution name ID.
     *
     * @return the institution name ID.
     */
    public int getInstitutionNamesID() {
        return institutionNamesID;
    }

    /**
     * Sets the institution name ID.
     *
     * @param institutionNamesID the institution name ID to set.
     */
    public void setInstitutionNamesID(int institutionNamesID) {
        this.institutionNamesID = institutionNamesID;
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
