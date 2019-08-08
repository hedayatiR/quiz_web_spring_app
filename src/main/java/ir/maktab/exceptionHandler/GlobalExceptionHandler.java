package ir.maktab.exceptionHandler;

import ir.maktab.service.student.exception.PasswordMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(DisabledException.class)
//    public final ResponseEntity<String> handleDisabledException(Exception ex) {
//        System.out.println("Global DisabledException captured");
//        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(PasswordMismatchException.class)
    public final ResponseEntity<String> handlePasswordMismatchException(Exception ex) {
        System.out.println("Global PasswordMismatchException captured");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }


}
