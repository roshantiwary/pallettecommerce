package com.pallette.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pallette.web.security.CustomAuthenticationEntryPoint;
import com.pallette.web.security.LogoutSuccessHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	 
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/oauth/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable()
                .headers()
                .frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/registration").permitAll()
                .antMatchers("/rest/**").authenticated()
                .antMatchers("/private/rest/api/v1/userprofile/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/private/rest/api/v1/userprofile/user").hasAnyAuthority("USER")
                .antMatchers("/private/rest/api/v1/userprofile/administrator").hasAnyAuthority("ADMINISTRATOR");

    }

}
