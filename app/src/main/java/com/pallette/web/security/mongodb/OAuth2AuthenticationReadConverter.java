package com.pallette.web.security.mongodb;
import com.pallette.user.User;
import com.pallette.user.api.ApiUser;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 23/05/2013
 */
//Hackery to deserialize back into an OAuth2Authentication Object made necessary because Spring Mongo can't map clientAuthentication to authorizationRequest
@ReadingConverter
@SuppressWarnings("rawtypes")
public class OAuth2AuthenticationReadConverter implements Converter<Document, OAuth2Authentication> {

    @Override
    @SuppressWarnings("unchecked")
    public OAuth2Authentication convert(Document source) {
    	Document storedRequest = (Document)source.get("storedRequest");

        OAuth2Request oAuth2Request = new OAuth2Request((Map<String, String>)storedRequest.get("requestParameters"),
                (String)storedRequest.get("clientId"), null, true, new HashSet((List)storedRequest.get("scope")),
                null, null, null, null);
        Document userAuthorization = (Document)source.get("userAuthentication");
        Authentication userAuthentication = null;
        if(userAuthorization != null) {
	        Object principal = getPrincipalObject(userAuthorization.get("principal"));
	        userAuthentication = new UsernamePasswordAuthenticationToken(principal,
                (String)userAuthorization.get("credentials"), getAuthorities((List) userAuthorization.get("authorities")));
	        ApiUser apiUser = getUserDetails(userAuthorization);
	        ((UsernamePasswordAuthenticationToken)userAuthentication).setDetails(apiUser);
        }
        OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request,
                userAuthentication );
        if(userAuthentication != null) {
        	authentication.setDetails(userAuthentication.getDetails());
        }
        return authentication;
    }

	private ApiUser getUserDetails(Document userAuthorization) {
		Document userMap = (Document) userAuthorization.get("details");
		User user = new User();
		user.setEmailAddress((String) userMap.get("emailAddress"));
		user.setFirstName((String) userMap.get("firstName"));
		user.setLastName((String) userMap.get("lastName"));
		user.setAge((int) userMap.get("age"));
		ApiUser apiUser = new ApiUser(user);
		apiUser.setId((Long) userMap.get("_id"));
		return apiUser;
	}

    private Object getPrincipalObject(Object principal) {
        if(principal instanceof Document) {
            Document principalDocument = (Document)principal;
            User user = new User(principalDocument);
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