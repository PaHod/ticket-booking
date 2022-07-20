package com.pahod.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class TicketAppExceptionHandler extends AbstractErrorController {

    private static final String ERROR_PATH = "/error";
    private final ErrorAttributes errorAttributes;

    @Autowired
    public TicketAppExceptionHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @ExceptionHandler(value = {TicketAppException.class})
    public String resourceNotFoundException(TicketAppException ex, HttpServletRequest request, Model model) {
        HttpStatus status = getStatus(request);

        Map<String, Object> errors = new HashMap<>();
        errors.put("status", status);
        errors.put("message", ex.getMessage());
        errors.put("internalCode", ex.internalCode());

        model.addAllAttributes(errors);
        return "error/error-" + status.value();
    }
}