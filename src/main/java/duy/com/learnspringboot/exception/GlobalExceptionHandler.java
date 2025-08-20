package duy.com.learnspringboot.exception;

import duy.com.learnspringboot.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Generic handler for all uncaught exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneralException(Exception ex) {
        return buildErrorResponse(ex, ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(BadRequestException ex) {
        return buildErrorResponse(ex, ex.getErrorCode(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildErrorResponse(ex, ex.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;

        log.error("Validation failed: {}", errors);
        ApiResponse<?> apiResponse = new ApiResponse<>(errorCode.getCode(), errorCode.getMessage());
        apiResponse.setErrors(errors);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return buildErrorResponse(ex, ex.getErrorCode(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return buildErrorResponse(ex, ErrorCode.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return buildErrorResponse(ex, ErrorCode.FORBIDDEN, HttpStatus.FORBIDDEN);
    }

    /**
     * Common method to log and build error responses
     */
    private ResponseEntity<ApiResponse<?>> buildErrorResponse(Exception ex, ErrorCode errorCode, HttpStatus status) {
        // Error log with stack trace for developers
        log.error("Exception caught [{}]: Code={}, Message={}, Details={}",
                ex.getClass().getSimpleName(),
                errorCode.getCode(),
                errorCode.getMessage(),
                ex.getMessage(),
                ex // logs stack trace
        );

        // Build API response for clients
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(status).body(apiResponse);
    }
}