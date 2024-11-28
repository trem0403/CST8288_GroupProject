package enums;

/**
 * Enum representing the delivery method for a course.
 */
public enum DeliveryMethod {
    IN_PERSON("In-Person"),
    REMOTE("Remote"),
    HYBRID("Hybrid");

    private final String value;

    /**
     * Constructor to initialize the enum with its corresponding string value.
     *
     * @param value the string representation of the delivery method.
     */
    DeliveryMethod(String value) {
        this.value = value;
    }

    /**
     * Gets the string value of the delivery method.
     *
     * @return the string representation of the delivery method.
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
    public static DeliveryMethod fromString(String value) {
        for (DeliveryMethod method : DeliveryMethod.values()) {
            if (method.value.equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid delivery method: " + value);
    }
}
