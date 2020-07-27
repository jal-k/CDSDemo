package com.cds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * @author Jalpa.Kholiya
 * Application class with Main method
 */
@SpringBootApplication
public class CdsdemoApplication extends SpringBootServletInitializer{

	private static final Logger logger = LoggerFactory.getLogger(CdsdemoApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CdsdemoApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		logger.debug("returning from configure()");
		return builder.sources(CdsdemoApplication.class);
	}
}
