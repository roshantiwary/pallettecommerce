package com.pallette.resource;

import com.pallette.oauth2.AuthorizationException;
import com.pallette.user.User;
import com.pallette.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Optional;

import javax.ws.rs.core.SecurityContext;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 07/05/2013
 */
public class BaseResource {

    @Autowired
    private UserRepository userRepository;

    //TODO: Cache to reduce calls to userRepository
    protected User ensureUserIsAuthorized(SecurityContext securityContext, String userId) {
        User user = loadUserFromSecurityContext(securityContext);
        if (user != null && (user.getId().equals(userId) || user.getEmailAddress().equals(userId.toLowerCase()))) {
            return user;
        }
        throw new AuthorizationException("User not permitted to access this resource");

    }

    protected User loadUserFromSecurityContext(SecurityContext securityContext) {
        OAuth2Authentication requestingUser = (OAuth2Authentication) securityContext.getUserPrincipal();
        Object principal = requestingUser.getUserAuthentication().getPrincipal();
        Optional<User> user = null;
        User returnUser = null;
        if(principal instanceof User) {
        	returnUser = (User)principal;
        } else {
        	user = userRepository.findById((Long)principal);
        	returnUser = user.get();
        }
        return returnUser;
    }
}
