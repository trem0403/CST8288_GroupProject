package model;

/**
 * Enum representing the schedule for a course.
 */
public enum Schedule {
    MORNING("Morning"),
    AFTERNOON("Afternoon"),
    EVENING("Evening");

    private final String value;

    /**
     * Constructor to initialize the enum with its corresponding string value.
     *
     * @param value the string representation of the schedule.
     */
    Schedule(String value) {
        this.value = value;
    }

    /**
     * Gets the string value of the schedule.
     *
     * @return the string representation of the schedule.
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
    public static Schedule fromString(String value) {
        for (Schedule schedule : Schedule.values()) {
            if (schedule.value.equalsIgnoreCase(value)) {
                return schedule;
            }
        }
        throw new IllegalArgumentException("Invalid schedule: " + value);
    }
}
