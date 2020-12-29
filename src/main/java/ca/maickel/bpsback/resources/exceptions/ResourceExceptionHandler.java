package ca.maickel.bpsback.resources.exceptions;

import ca.maickel.bpsback.resources.StandardError;
import ca.maickel.bpsback.services.exceptions.AuthorizationException;
import ca.maickel.bpsback.services.exceptions.FileException;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
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

  @ExceptionHandler(FileException.class)
  public ResponseEntity<StandardError> file(
          FileException error, HttpServletRequest request) {
    StandardError err =
            new StandardError(
                    HttpStatus.BAD_REQUEST.value(), error.getMessage(), System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(AmazonServiceException.class)
  public ResponseEntity<StandardError> amazonService(
          AmazonServiceException error, HttpServletRequest request) {
    StandardError err =
        new StandardError(
            HttpStatus.valueOf(error.getErrorCode()).value(),
            error.getMessage(),
            System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.valueOf(error.getErrorCode())).body(err);
  }

  @ExceptionHandler(AmazonClientException.class)
  public ResponseEntity<StandardError> amazonClient(
          AmazonClientException error, HttpServletRequest request) {
    StandardError err =
            new StandardError(
                    HttpStatus.BAD_REQUEST.value(),
                    error.getMessage(),
                    System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(AmazonS3Exception.class)
  public ResponseEntity<StandardError> amazonS3(
          AmazonS3Exception error, HttpServletRequest request) {
    StandardError err =
            new StandardError(
                    HttpStatus.BAD_REQUEST.value(),
                    error.getMessage(),
                    System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }
}
