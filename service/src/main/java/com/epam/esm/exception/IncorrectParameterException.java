package com.epam.esm.exception;

public class IncorrectParameterException extends RuntimeException {
    private final ExceptionResult exceptionResult;

    public IncorrectParameterException(ExceptionResult exceptionResult) {
        this.exceptionResult = exceptionResult;
    }

    public ExceptionResult getExceptionResult() {
        return exceptionResult;
    }
}