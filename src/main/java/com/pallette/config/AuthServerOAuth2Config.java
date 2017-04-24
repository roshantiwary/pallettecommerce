package com.pallette.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.pallette.web.security.mongodb.OAuth2AccessTokenRepository;
import com.pallette.web.security.mongodb.OAuth2RefreshTokenRepository;
import com.pallette.web.security.mongodb.OAuth2RepositoryTokenStore;
@Configuration
@EnableAuthorizationServer
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private OAuth2AccessTokenRepository oAuth2AccessTokenRepository;
	
	@Autowired
	private OAuth2RefreshTokenRepository oAuth2RefreshTokenRepository;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		security
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
        .allowFormAuthenticationForClients();
	}
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) 
      throws Exception {
		
		clients.inMemory()
        .withClient("acme")
        .secret("acmesecret")
        .authorizedGrantTypes("authorization_code", "refresh_token",
            "password", "client_credentials").scopes("read","write")
        .accessTokenValiditySeconds(1800);
		
    }
	
	@Bean
	public TokenStore tokenStore() {
		return new OAuth2RepositoryTokenStore(oAuth2AccessTokenRepository, oAuth2RefreshTokenRepository);
	}
	
 
    @Override
    public void configure(
      AuthorizationServerEndpointsConfigurer endpoints) 
      throws Exception {
    	
    	endpoints.authenticationManager(authenticationManager)
    	.tokenStore(tokenStore())
//    	.tokenEnhancer(tokenEnhancer())
    	.allowedTokenEndpointRequestMethods(HttpMethod.GET);
    	
    }
    
//    @Bean
//    public TokenEnhancer tokenEnhancer() {
//        return new CustomTokenEnhancer();
//    }
 
}
