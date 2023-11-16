package Test.Kalvad.ExceptionHandling;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {


    // Handling NotFoundException with a customized Message
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorDetails message = new ErrorDetails(
                ex.getStatusCode(),
                ex.getTimestamp(),
                ex.getMessage(),
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    // Handling Duplicated Entries with a customized Message.
    @ExceptionHandler(DuplicatedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDetails> DuplicateEntryException(DuplicatedException ex, WebRequest request) {
        ErrorDetails message = new ErrorDetails(
                ex.getStatusCode(),
                ex.getTimestamp(),
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    //handling invalid arguments.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public InvalidArguments handleInvalidException(MethodArgumentNotValidException ex) {

        Map<String, String> errorsMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        });


        return new InvalidArguments("Invalid Data",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                errorsMap
        );
    }


    //Data Access Exception.
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDetails> handleDatabaseException(DataAccessException ex, WebRequest request) {
          ErrorDetails message = new ErrorDetails(
                  HttpStatus.CONFLICT,
                  LocalDateTime.now(),
                  "Connection to the database Failed",
                  request.getDescription(false));

        return  ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }



    //handling Bad Credentials exception
    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<ErrorDetails> handleAuthenticationException(WebRequest request) {
        ErrorDetails message = new ErrorDetails(
                HttpStatus.UNAUTHORIZED,
                LocalDateTime.now(),
                "Bad Credentials or Username Not found"
                , request.getDescription(false));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }





}
