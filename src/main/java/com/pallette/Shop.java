package com.pallette;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pallette.config.CascadingMongoEventListener;
import com.pallette.web.security.mongodb.OAuth2AuthenticationReadConverter;

@SpringBootApplication
@EnableScheduling
@EnableOAuth2Client
@EnableTransactionManagement
public class Shop extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		setRegisterErrorPageFilter(false);
		return application.sources(Shop.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Shop.class, args);
	}

	@Value("${security.oauth2.client.access-token-uri}")
	private String accessTokenUri;

	@Value("${security.oauth2.client.grant-type}")
	private String grantType;

	@Value("${security.oauth2.client.client-id}")
	private String clientID;

	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;

	/**
	 * The heart of our interaction with the resource; handles redirection for
	 * authentication, access tokens, etc.
	 * 
	 * @param oauth2ClientContext
	 * @return
	 */
	@Bean
	public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
		return new OAuth2RestTemplate(resource(), oauth2ClientContext);
	}

	@Bean
	public CascadingMongoEventListener cascadingMongoEventListener() {
		return new CascadingMongoEventListener();
	}

	@Bean
	protected OAuth2ProtectedResourceDetails resource() {
		List scopes = new ArrayList<String>(2);
		scopes.add("write");
		scopes.add("read");
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setClientId(clientID);
		resource.setClientSecret(clientSecret);
		resource.setAccessTokenUri(accessTokenUri);
		resource.setGrantType(grantType);
		resource.setClientAuthenticationScheme(AuthenticationScheme.header);
		resource.setTokenName("access_token");
		resource.setScope(scopes);
		resource.setClientAuthenticationScheme(AuthenticationScheme.form);
		return resource;
	}

	@Bean
	public CustomConversions customConversions() {
		List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
		OAuth2AuthenticationReadConverter converter = new OAuth2AuthenticationReadConverter();
		converterList.add(converter);
		return new CustomConversions(converterList);
	}

	@Bean
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext context) {

		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
		converter.setCustomConversions(customConversions());
		converter.afterPropertiesSet();

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

		return mongoTemplate;

	}

	@Bean
	public Validator validator() {
		return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
	}

    @Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
    
}
