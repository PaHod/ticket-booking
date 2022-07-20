package com.pahod.exception;

public class TicketAppException extends RuntimeException {

    private final int internalCode;

    public TicketAppException(int internalCode, String message) {
        super(message);
        this.internalCode = internalCode;
    }

    public int internalCode() {
        return internalCode;
    }
}