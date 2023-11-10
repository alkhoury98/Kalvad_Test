package Test.Kalvad.ExceptionHandling;

import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {


    // Handling NotFoundException with a customized Message
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDetails resourceNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorDetails message = new ErrorDetails(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    // Handling Duplicated Entries with a customized Message.
    @ExceptionHandler(DuplicatedException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDetails DuplicateEntryException(DuplicatedException ex, WebRequest request) {
        ErrorDetails message = new ErrorDetails(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorsMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorsMap;
    }
}
