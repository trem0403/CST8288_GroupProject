package enums;

/**
 * Enum representing the role of a user in the system.
 */
public enum Role {
    ACADEMIC_PROFESSIONAL("AcademicProfessional"),
    ACADEMIC_INSTITUTION("AcademicInstitution");

    private final String value;

    /**
     * Constructor to initialize the enum with its corresponding string value.
     *
     * @param value the string representation of the role.
     */
    Role(String value) {
        this.value = value;
    }

    /**
     * Gets the string value of the role.
     *
     * @return the string representation of the role.
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
    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
}
