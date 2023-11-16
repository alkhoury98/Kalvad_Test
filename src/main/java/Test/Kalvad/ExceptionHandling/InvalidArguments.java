package Test.Kalvad.ExceptionHandling;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class InvalidArguments {

    private final String message;

    private final LocalDateTime timestamp;

    private final HttpStatus statusCode;

    private final Map<String, String> errorDetails;

    public InvalidArguments(String message, LocalDateTime timestamp, HttpStatus statusCode, Map<String, String> errorDetails) {
        this.message = message;
        this.statusCode = HttpStatus.BAD_REQUEST;
        this.timestamp = LocalDateTime.now();
        this.errorDetails = errorDetails;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getErrorDetails() {
        return errorDetails;
    }
}
