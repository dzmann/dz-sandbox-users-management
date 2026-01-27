package dz.sandbox.users.management.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dz.sandbox.users.management.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    ErrorDto body =
        ErrorDto.builder()
            .timestamp(LocalDateTime.now())
            .message("Forbidden")
            .details("You don't have enough permissions to access this resource")
            .status(403)
            .build();

    response.getWriter().write(objectMapper.writeValueAsString(body));
  }
}
