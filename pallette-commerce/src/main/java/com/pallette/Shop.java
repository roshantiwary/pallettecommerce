package com.pallette;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Shop extends SpringBootServletInitializer{
	    
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		setRegisterErrorPageFilter(false);
        return application.sources(Shop.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Shop.class, args);	
	}
	
}
