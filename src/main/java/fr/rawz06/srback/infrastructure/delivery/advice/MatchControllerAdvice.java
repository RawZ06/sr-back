package fr.rawz06.srback.infrastructure.delivery.advice;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.exception.business.NullAvailabilityException;
import fr.rawz06.srback.core.exception.business.RestreamerNotFoundException;
import fr.rawz06.srback.core.exception.business.SameStaffException;
import fr.rawz06.srback.infrastructure.delivery.exception.BadAvailabilitySetException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MatchControllerAdvice {

    @ExceptionHandler(BadAvailabilitySetException.class)
    public ResponseEntity<String> handleBadAvailabilitySetException(BadAvailabilitySetException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RestreamerNotFoundException.class)
    public ResponseEntity<String> handleRestreamerNotFoundException(RestreamerNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(SameStaffException.class)
    public ResponseEntity<String> handleSameStaffException(SameStaffException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NullAvailabilityException.class)
    public ResponseEntity<String> handleNullAvailabilityException(NullAvailabilityException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<String> handleMatchNotFoundException(MatchNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
