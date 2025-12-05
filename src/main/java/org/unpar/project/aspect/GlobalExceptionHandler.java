package org.unpar.project.aspect;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=SecurityException.class)
    public String handleSecurityException() {
        return "redirect:/login";
    }
}
