package com.epam.esm.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionResult {
    private final Map<String, Object[]> exceptionMessages;

    public ExceptionResult() {
        exceptionMessages = new HashMap<>();
    }

    public void addException(String messageCode, Object... arguments) {
        exceptionMessages.put(messageCode, arguments);
    }

    public Map<String, Object[]> getExceptionMessages() {
        return exceptionMessages;
    }
}
