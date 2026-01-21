package dz.sandbox.users.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {

    private int status;
    private String message;
    private String details;
    private LocalDateTime timestamp;
}
