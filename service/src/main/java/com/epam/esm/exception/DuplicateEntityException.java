package com.epam.esm.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String messageCode) {
        super(messageCode);
    }

}
