package com.pahod.exception;


public class UserNotFoundException extends TicketAppException {

    public static final int CODE = 222;

    public UserNotFoundException(long doctorId) {
        super(CODE, "Not found user with id=" + doctorId);
    }
}
