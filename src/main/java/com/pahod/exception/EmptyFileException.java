package com.pahod.exception;


public class EmptyFileException extends TicketAppException {

    public static final int CODE = 321;

    public EmptyFileException(String s) {
        super(CODE, String.format("Provided file : %s \n is empty", s));
    }
}
