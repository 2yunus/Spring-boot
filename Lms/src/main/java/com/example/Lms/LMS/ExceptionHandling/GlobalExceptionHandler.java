package com.example.Lms.LMS.ExceptionHandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundError(NoHandlerFoundException ex) {
        return "error-404"; // Return the name of the error template for 404
    }
}
