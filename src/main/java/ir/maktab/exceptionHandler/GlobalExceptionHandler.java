package ir.maktab.exceptionHandler;

import ir.maktab.service.course.exception.InvalidInputException;
import ir.maktab.service.user.exception.PasswordMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(PasswordMismatchException.class)
    public final ResponseEntity<String> handlePasswordMismatchException(Exception ex) {
        System.out.println("Global PasswordMismatchException captured");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    public final ResponseEntity<String> handleInvalidInputException(Exception ex) {
        System.out.println("Global handleInvalidInputException captured");
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }





}
