package com.epam.esm.exception;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String messageCode) {
        super(messageCode);
    }

}
