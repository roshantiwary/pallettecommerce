package com.pallette.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.pallette.web.security.CustomAuthenticationEntryPoint;
import com.pallette.web.security.CustomLogoutSuccessHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	 
	@Autowired
	private CustomLogoutSuccessHandler logoutSuccessHandler;
	
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
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/registration").permitAll()
                .antMatchers("/rest/**").authenticated()
                .antMatchers("/private/rest/api/v1/userprofile/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/private/rest/api/v1/userprofile/user").hasAnyAuthority("USER")
                .antMatchers("/private/rest/api/v1/userprofile/administrator").hasAnyAuthority("ADMINISTRATOR")
                .antMatchers("/private/rest/api/v1/userprofile/account/**").hasAnyAuthority("USER");

    }
	
	@Bean
	public CorsFilter corsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true); 
	    config.addAllowedOrigin("http://localhost:4200");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("GET");
	    config.addAllowedMethod("PUT");
	    config.addAllowedMethod("POST");
	    config.addAllowedMethod("DELETE");
	    config.addAllowedMethod("PATCH");
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}

}
