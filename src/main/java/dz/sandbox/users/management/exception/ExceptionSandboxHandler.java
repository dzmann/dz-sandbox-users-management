package dz.sandbox.users.management.exception;

import dz.sandbox.users.management.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ExceptionSandboxHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorDto> handleBadRequest(
      IllegalArgumentException ex, HttpServletRequest request) {
    ErrorDto error =
        new ErrorDto(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(),
            LocalDateTime.now());

    return ResponseEntity.badRequest().body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDto> handleGenericException(Exception ex, HttpServletRequest request) {
    ErrorDto error =
        new ErrorDto(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "Unexpected error ocurred",
            LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(SandboxException.class)
  public ResponseEntity<ErrorDto> handleGenericException(
      SandboxException ex, HttpServletRequest request) {
    ErrorDto error =
        new ErrorDto(ex.getStatus(), ex.getMessage(), ex.getDetails(), LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.valueOf(ex.getStatus())).body(error);
  }
}
