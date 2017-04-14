package com.pallette.web.security.mongodb;
import com.mongodb.DBObject;
import com.pallette.domain.Account;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 23/05/2013
 */
//Hackery to deserialize back into an OAuth2Authentication Object made necessary because Spring Mongo can't map clientAuthentication to authorizationRequest
@ReadingConverter
@SuppressWarnings("rawtypes")
public class OAuth2AuthenticationReadConverter implements Converter<DBObject, OAuth2Authentication> {

    @Override
    @SuppressWarnings("unchecked")
    public OAuth2Authentication convert(DBObject source) {
        System.out.println(source);
        DBObject storedRequest = (DBObject)source.get("storedRequest");

        OAuth2Request oAuth2Request = new OAuth2Request((Map<String, String>)storedRequest.get("requestParameters"),
                (String)storedRequest.get("clientId"), null, true, new HashSet((List)storedRequest.get("scope")),
                null, null, null, null);
        DBObject userAuthorization = (DBObject)source.get("userAuthentication");
        Authentication userAuthentication = null;
        if(userAuthorization != null) {
	        Object principal = getPrincipalObject(userAuthorization.get("principal"));
	        userAuthentication = new UsernamePasswordAuthenticationToken(principal,
                (String)userAuthorization.get("credentials"), getAuthorities((List) userAuthorization.get("authorities")));
        }
        OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request,
                userAuthentication );
        return authentication;
    }

    private Object getPrincipalObject(Object principal) {
        if(principal instanceof DBObject) {
            DBObject principalDBObject = (DBObject)principal;
            Account user = new Account(principalDBObject);
            return user;
        } else {
            return principal;
        }
    }

    private Collection<GrantedAuthority> getAuthorities(List<Map<String, String>> authorities) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>(authorities.size());
        for(Map<String, String> authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.get("role")));
        }
        return grantedAuthorities;
    }

}