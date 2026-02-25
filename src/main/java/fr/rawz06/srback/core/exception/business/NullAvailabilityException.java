package fr.rawz06.srback.core.exception.business;

public class NullAvailabilityException extends Exception {
    public NullAvailabilityException() {
        super("availability is null");
    }
}
