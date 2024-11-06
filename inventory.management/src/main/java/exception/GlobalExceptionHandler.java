package exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import model.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        return buildErrorResponse(ex.getMessage(), ex.getStatus().value(), ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return buildErrorResponse("An unexpected error occurred: " + ex.getMessage(), 
                                  HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                                  HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, int statusCode, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(
                message,
                statusCode,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, status);
    }
}	