package Test.Kalvad.ExceptionHandling;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class NotFoundException extends RuntimeException {

    private final LocalDateTime timestamp;

    private final HttpStatus statusCode;

    public NotFoundException(String msg, LocalDateTime timestamp, HttpStatus statusCode) {
        super(msg);
        this.statusCode = HttpStatus.NOT_FOUND;
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public HttpStatus getStatusCode() {
        return statusCode;
    }

}