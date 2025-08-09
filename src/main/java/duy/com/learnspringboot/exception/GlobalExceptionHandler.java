package duy.com.learnspringboot.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { HttpMessageNotReadableException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(new Date());
        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));

        String message = e.getMessage();

        if (e instanceof HttpMessageNotReadableException) {
            message = message.substring(message.indexOf("JSON parse error:"));
        } else if (e instanceof MethodArgumentNotValidException) {
            int startIndex = message.lastIndexOf("[");
            int endIndex = message.lastIndexOf("]");
            message = message.substring(startIndex + 1, endIndex - 1);
        } else if (e instanceof ConstraintViolationException) {
            int startIndex = message.indexOf("[");
            int endIndex = message.indexOf("]");
            message = message.substring(startIndex + 1, endIndex + 1);
        }

        errorResponse.setMessage(message);


        return errorResponse;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimestamp(new Date());
        errorResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));

        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }

//    @ExceptionHandler(value = Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleException(Exception e, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        errorResponse.setTimestamp(new Date());
//        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
//        errorResponse.setMessage("An unexpected error occurred");
//
//        return errorResponse;
//    }
}
