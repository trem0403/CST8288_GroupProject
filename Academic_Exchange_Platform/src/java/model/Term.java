package model;

/**
 * Represents a term in the academic calendar.
 */
public class Term {

    private int termID; // Primary key
    private String term;

    /**
     * Constructor to create a Term object.
     *
     * @param termID the unique ID of the term.
     * @param term the term description.
     */
    public Term(int termID, String term) {
        this.termID = termID;
        this.term = term;
    }

    /**
     * Gets the term ID.
     *
     * @return the term ID.
     */
    public int getTermID() {
        return termID;
    }

    /**
     * Sets the term ID.
     *
     * @param termID the term ID to set.
     */
    public void setTermID(int termID) {
        this.termID = termID;
    }

    /**
     * Gets the term description.
     *
     * @return the term description.
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the term description.
     *
     * @param term the term description to set.
     */
    public void setTerm(String term) {
        this.term = term;
    }
}
