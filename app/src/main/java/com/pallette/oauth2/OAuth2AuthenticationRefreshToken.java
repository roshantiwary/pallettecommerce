package com.pallette.oauth2;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.pallette.persistence.BaseEntity;

/**
 * @version 1.0
 * @author: Roshan
 */

public class OAuth2AuthenticationRefreshToken extends BaseEntity{
	private String tokenId;
	private OAuth2RefreshToken oAuth2RefreshToken;
    private OAuth2Authentication authentication;
    
    public String getTokenId() {
		return tokenId;
	}
	
    public OAuth2RefreshToken getoAuth2RefreshToken() {
		return oAuth2RefreshToken;
	}
	
    public OAuth2Authentication getAuthentication() {
		return authentication;
	}
    
    public OAuth2AuthenticationRefreshToken(OAuth2RefreshToken oAuth2RefreshToken, OAuth2Authentication authentication) {
        this.oAuth2RefreshToken = oAuth2RefreshToken;
        this.authentication = authentication;
        this.tokenId = oAuth2RefreshToken.getValue();
    }
}
