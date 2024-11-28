package enums;

/**
 * Enum representing the status of a request to teach.
 */
public enum Status {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected");

    private final String value;

    /**
     * Constructor to initialize the enum with its corresponding string value.
     *
     * @param value the string representation of the status.
     */
    Status(String value) {
        this.value = value;
    }

    /**
     * Gets the string value of the status.
     *
     * @return the string representation of the status.
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the enum constant corresponding to a given string value.
     *
     * @param value the string value.
     * @return the enum constant.
     */
    public static Status fromString(String value) {
        for (Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }
}
