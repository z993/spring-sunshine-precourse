package sunshine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sunshine.domain.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        ApiErrorResponse body = new ApiErrorResponse("ERROR", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }
}
