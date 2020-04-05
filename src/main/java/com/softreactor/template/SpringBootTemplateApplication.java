package com.softreactor.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring boot application class.
 */
@SpringBootApplication
public class SpringBootTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTemplateApplication.class, args);
	}

	/**
	 * Configuration properties bean creator.
	 */
	@Bean
	public Properties configProperties() throws IOException {
		Properties properties = new Properties();
		try (InputStream stream = this.getClass().getResourceAsStream("/config.properties")) {
			properties.load(stream);
		}

		return properties;
	}
}
