package org.reggiemcdonald.api.advice;

import org.reggiemcdonald.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice
public class NeuralServExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidArgumentExceptionHandler(MethodArgumentNotValidException e) {
        String fieldName = e.getBindingResult().getFieldError().getField();
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return String.format("Parameter '%s' %s", fieldName, msg);
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String dataIntegrityExceptionHandler(DataIntegrityViolationException e) {
        return e.getMostSpecificCause().getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MalformedRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String malformedRequestExceptionHandler(MalformedRequestException e) {
        return String.format("The request to %s failed because %s", e.getEndpoint(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NeuralservInternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String neuralServInternalServerException(NeuralservInternalServerException e) {
        return "The request failed due to a server-side problem";
    }
}
