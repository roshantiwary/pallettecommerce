package com.pallette.user.exception;

import com.pallette.exception.BaseWebApplicationException;

public class DuplicateUserException extends BaseWebApplicationException {

    public DuplicateUserException() {
        super(409, "User already exists", "An attempt was made to create a user that already exists");
    }
}

