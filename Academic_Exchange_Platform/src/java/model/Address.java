package model;

/**
 * Represents an address entity for an academic institution.
 * Identified by a unique ZIP code.
 */
public class Address {

    private int zip; // Primary key
    private String country;
    private String state;
    private String city;
    private String street;

    /**
     * Constructor to create an Address object.
     *
     * @param zip the ZIP code.
     * @param country the country.
     * @param state the state.
     * @param city the city.
     * @param street the street.
     */
    public Address(int zip, String country, String state, String city, String street) {
        this.zip = zip;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
    }

    /**
     * Gets the ZIP code.
     *
     * @return the ZIP code.
     */
    public int getZip() {
        return zip;
    }

    /**
     * Sets the ZIP code.
     *
     * @param zip the ZIP code to set.
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * Gets the country.
     *
     * @return the country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country.
     *
     * @param country the country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the state.
     *
     * @return the state.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state the state to set.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the city.
     *
     * @return the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the street.
     *
     * @return the street.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street.
     *
     * @param street the street to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }
}
