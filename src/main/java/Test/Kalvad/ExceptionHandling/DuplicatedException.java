package Test.Kalvad.ExceptionHandling;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class DuplicatedException extends RuntimeException {

    private final LocalDateTime timestamp;
    private final HttpStatus statusCode;

    public DuplicatedException(String message, LocalDateTime timestamp, HttpStatus statusCode) {
        super(message);
        this.timestamp = timestamp;
        this.statusCode = statusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
