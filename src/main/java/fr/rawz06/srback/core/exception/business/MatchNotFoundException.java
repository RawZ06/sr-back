package fr.rawz06.srback.core.exception.business;

public class MatchNotFoundException extends Exception {
    public MatchNotFoundException(long message) {
        super("Match not found: " + message);
    }
}
