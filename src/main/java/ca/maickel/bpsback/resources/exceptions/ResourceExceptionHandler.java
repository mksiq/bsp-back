package ca.maickel.bpsback.resources.exceptions;

import ca.maickel.bpsback.resources.StandardError;
import ca.maickel.bpsback.services.exceptions.AuthorizationException;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardError> objectNotFound(
      ObjectNotFoundException error, HttpServletRequest request) {
    StandardError err =
        new StandardError(
            HttpStatus.NOT_FOUND.value(), error.getMessage(), System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<StandardError> authorization(
      AuthorizationException error, HttpServletRequest request) {
    StandardError err =
        new StandardError(
            HttpStatus.FORBIDDEN.value(), error.getMessage(), System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
  }
}
