package cm.g2s.loan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected final ResponseEntity<ExceptionDetails> handleBadRequestException(BadRequestException e,
                                                                               WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),  e.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected final ResponseEntity<ExceptionDetails> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                                WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),  e.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected final ResponseEntity<Map> handleConstraintViolationException(ConstraintViolationException ex,
                                                                  WebRequest request){
        Map<String, Object> errorsMap = new LinkedHashMap<>();

        ex.getConstraintViolations().forEach(constraintViolation -> {
            errorsMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.toString());
        });
        errorsMap.put("timestamp", LocalDateTime.now());
        errorsMap.put("status", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStorageException.class)
    protected final ResponseEntity<ExceptionDetails> handleFileStorageException(FileStorageException e,
                                                                                    WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),  e.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    protected final ResponseEntity<ExceptionDetails> handleAppException(AppException e,
                                                                            WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),  e.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConflictException.class)
    protected final ResponseEntity<ExceptionDetails> handleConflictException(ConflictException e,
                                                                                 WebRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.CONFLICT.value(),
                LocalDateTime.now(), e.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionDetails, HttpStatus.CONFLICT);
    }


}
