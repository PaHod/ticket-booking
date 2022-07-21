package com.pahod.exception;


public class PageException extends TicketAppException {

    public static final int CODE = 321;

    public PageException(String s) {
        super(CODE, String.format("Provided file : %s \n is empty", s));
    }
}
