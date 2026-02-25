package fr.rawz06.srback.core.exception.business;

public class RestreamerNotFoundException extends Exception {
    public RestreamerNotFoundException(String name) {
        super("Restreamer " + name + " not found.");
    }
}
