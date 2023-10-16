package simlabapp.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import simlabapp.dto.response.WebResponse;

@RestControllerAdvice
public class ExceptionController{

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<?>> responseStatusException(ResponseStatusException exception) {
        WebResponse<?> response = WebResponse.builder()
                .code(exception.getStatusCode().value())
                .status(getErrorMessage(exception.getStatusCode().value()))
                .data(exception.getReason())
                .build();
        return ResponseEntity.status(exception.getStatusCode()).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<?>> constraintViolationException(ConstraintViolationException exception) {
        WebResponse<?> response = WebResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .status(getErrorMessage(HttpStatus.BAD_REQUEST.value()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<?>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        WebResponse<?> response = WebResponse.builder()
                .code(ex.getStatusCode().value())
                .status(getErrorMessage(ex.getStatusCode().value()))
                .data(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponse<?>> exception(Exception exception) {
        WebResponse<?> response = WebResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(getErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .data(exception.getMessage())
                .build();
        return ResponseEntity.internalServerError().body(response);
    }

    private String getErrorMessage(int code) {
        if (code == 400) {
            return "BAD_REQUEST";
        } else if (code == 404) {
            return "NOT_FOUND";
        } else if (code == 401) {
            return "UNAUTHORIZED";
        } else if (code == 403) {
            return "ACCESS_DENIED";
        } else {
            return "INTERNAL_SERVER_ERROR";
        }
    }

}
