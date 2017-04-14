package com.pallette.user.exception;

import com.pallette.exception.BaseWebApplicationException;

public class AuthenticationException extends BaseWebApplicationException {

    public AuthenticationException() {
        super(401, "Authentication Error", "Authentication Error. The username or password were incorrect");
    }
}