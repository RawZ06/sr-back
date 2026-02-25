package fr.rawz06.srback.core.exception.business;

public class SameStaffException extends Exception {
    public SameStaffException() {
        super("The host and the cohost cannot be the same person!");
    }
}
