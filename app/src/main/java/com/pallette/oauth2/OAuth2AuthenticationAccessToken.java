package com.pallette.oauth2;

import java.io.Serializable;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;

/**
 * @version 1.0
 * @author: Roshan
 */

public class OAuth2AuthenticationAccessToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6034700905994879044L;
	private String tokenId;
	private OAuth2AccessToken oAuth2AccessToken;
    private String authenticationId;
    private String userName;
    private String clientId;
    private OAuth2Authentication authentication;
    private String refreshToken;

    public OAuth2AuthenticationAccessToken(final OAuth2AccessToken oAuth2AccessToken, final OAuth2Authentication authentication, final String authenticationId) {
        this.tokenId = oAuth2AccessToken.getValue();
        this.oAuth2AccessToken = oAuth2AccessToken;
        this.authenticationId = authenticationId;
        this.userName = authentication.getName();
        this.clientId = authentication.getOAuth2Request().getClientId();
        this.authentication = authentication;
        this.refreshToken = oAuth2AccessToken.getRefreshToken().getValue();
        this.id = authenticationId;
    }
    
    public String getTokenId() {
		return tokenId;
	}

	public OAuth2AccessToken getoAuth2AccessToken() {
		return oAuth2AccessToken;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public String getUserName() {
		return userName;
	}

	public String getClientId() {
		return clientId;
	}

	public OAuth2Authentication getAuthentication() {
		return authentication;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
    
    private int version;

    @Id
    private String id;

    private Date timeCreated;

    public String getId() {
        return id;
    }

    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OAuth2AuthenticationAccessToken that = (OAuth2AuthenticationAccessToken) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    public int getVersion() {
        return version;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }
}
