package edu.matrix.co.schoolmanagementsystem.enums;

import org.springframework.lang.Nullable;

public enum StatusEnum {

    SUCCESS(0, "Success"),
    FAILURE(1, "Error or Exception Occurred.");


    private static final StatusEnum[] VALUES;

    static {
        VALUES = values();
    }

    private final String reasonPhrase;
    private int value;


    StatusEnum(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }


    StatusEnum(Object value, String reasonPhrase) {
        if (value instanceof Integer) {
            this.value = (int) value;
        }
        this.reasonPhrase = reasonPhrase;
    }

    /**
     * Return the {@code StatusEnum} enum constant with the specified numeric value.
     *
     * @param statusCode the numeric value of the enum to be returned
     * @return the enum constant with the specified numeric value
     * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
     */
    public static StatusEnum valueOf(int statusCode) {
        StatusEnum status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }

    /**
     * Resolve the given status code to an {@code StatusEnum}, if possible.
     *
     * @param statusCode the status code (potentially non-standard)
     * @return the corresponding {@code StatusEnum}, or {@code null} if not found
     * @since 5.0
     */
    @Nullable
    public static StatusEnum resolve(int statusCode) {
        // Use cached VALUES instead of values() to prevent array allocation.
        for (StatusEnum status : VALUES) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }

    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return this.value + " " + reasonPhrase;
    }


}