package fr.rawz06.srback.infrastructure.delivery.exception;

public class BadAvailabilitySetException extends Exception {
    public BadAvailabilitySetException(String availability) {
        super("Invalid availability set: " + availability);
    }
}
