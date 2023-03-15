package com.epam.esm.exception;

import com.epam.esm.config.MessageByLang;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Map;

import static com.epam.esm.exception.ExceptionCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(DuplicateEntityException.class)
    public final ResponseEntity<Object> handleDuplicateEntityExceptions(DuplicateEntityException ex) {
        String details = MessageByLang.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(CONFLICT_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public final ResponseEntity<Object> handleNoSuchEntityExceptions(NoSuchEntityException ex) {
        String details = MessageByLang.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public final ResponseEntity<Object> handleIncorrectParameterExceptions(IncorrectParameterException ex) {
        Iterator<Map.Entry<String, Object[]>> exceptions = ex.getExceptionResult().getExceptionMessages().entrySet().iterator();
        StringBuilder details = new StringBuilder();
        while (exceptions.hasNext()) {
            Map.Entry<String, Object[]> exception = exceptions.next();
            String message = MessageByLang.toLocale(exception.getKey());
            String detail = String.format(message, exception.getValue());
            details.append(detail).append(' ');
        }

        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST_EXCEPTION.toString(), details.toString());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public final ResponseEntity<Object> handleUnsupportedOperationExceptions() {
        String details = MessageByLang.toLocale("exception.unsupportedOperation");
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        String details = MessageByLang.toLocale("exception.badRequest");
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Object> handleBadRequestException() {
        String details = MessageByLang.toLocale("exception.noHandler");
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> methodNotAllowedExceptionException() {
        String details = MessageByLang.toLocale("exception.notSupported");
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse("40001", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(ConstraintViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse("40001", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(PSQLException ex) {
        ErrorResponse errorResponse = new ErrorResponse("40001", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
