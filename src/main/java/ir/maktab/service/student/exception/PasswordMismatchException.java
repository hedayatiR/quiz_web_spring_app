package ir.maktab.service.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password Mismatch")
public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(String message) {
        super(message);
    }
}
