package com.pallette.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import com.pallette.web.security.CustomAuthenticationProvider;
import com.pallette.web.security.CustomCredentialsService;
import com.pallette.web.security.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomCredentialsService customCredentialsService;
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Value("${security.oauth2.client.access-token-uri}")
	private String accessTokenUri;

    @Value("${security.oauth2.client.grant-type}")
    private String grantType;

    @Value("${security.oauth2.client.client-id}")
    private String clientID;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
        .authorizeRequests()
            .antMatchers("/", "/registration","/rest/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .permitAll()
            .and()
        .logout()
        .logoutSuccessHandler(logoutSuccessHandler)
            .permitAll();
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/webjars/**")
                .antMatchers("/images/**").antMatchers("/css/**").antMatchers("/js/**");
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider);
		auth.userDetailsService(customCredentialsService);
	}
	
	/**
     * The heart of our interaction with the resource; handles redirection for authentication, access tokens, etc.
     * @param oauth2ClientContext
     * @return
     */
    @Bean
    public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
        return new OAuth2RestTemplate(resource(), oauth2ClientContext);
    }

    private OAuth2ProtectedResourceDetails resource() {
        AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
        resource.setClientId(clientID);
        resource.setClientSecret(clientSecret);
        resource.setAccessTokenUri(accessTokenUri);
        resource.setGrantType(grantType);

        return resource;
    }

}
