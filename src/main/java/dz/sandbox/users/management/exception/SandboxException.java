package dz.sandbox.users.management.exception;

import lombok.Data;

@Data
public class SandboxException extends RuntimeException{

    private int status;
    private String details;

    public SandboxException(String message, int status, String details) {
        super(message);
        this.status = status;
        this.details = details;
    }
}
