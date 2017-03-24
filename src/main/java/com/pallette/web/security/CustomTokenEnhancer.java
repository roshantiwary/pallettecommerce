package com.pallette.web.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.pallette.repository.SequenceDao;

public class CustomTokenEnhancer implements TokenEnhancer{

	private static final String HOSTING_SEQ_KEY = "hosting";
	
	@Autowired
	private SequenceDao sequenceDao;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
			OAuth2Authentication authentication) {
		 Map<String, Object> additionalInfo = new HashMap<>();
	        additionalInfo.put("orderId", sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY));
	        additionalInfo.put("profileId", sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY));
	        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
	        return accessToken;
	}

}
