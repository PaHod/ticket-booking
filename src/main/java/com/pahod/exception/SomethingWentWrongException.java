package com.pahod.exception;


public class SomethingWentWrongException extends TicketAppException {

    public static final int CODE = 777;

    public SomethingWentWrongException(String s) {
        super(CODE, String.format("Something went wrong with: %s please again try later", s));
    }
}
