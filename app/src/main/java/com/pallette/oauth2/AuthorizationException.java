package com.pallette.oauth2;

import com.pallette.exception.BaseWebApplicationException;

public class AuthorizationException extends BaseWebApplicationException{
    public AuthorizationException(String applicationMessage) {
        super(403, "Not authorized", applicationMessage);
    }
}
