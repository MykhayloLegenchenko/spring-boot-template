package com.softreactor.template.config;

import java.util.Locale;

import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * MVC configuration class.
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
	private final MessageSource messageSource;
	
	/**
	 * Class constructor. 
	 */
	public MvcConfiguration(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver clr = new CookieLocaleResolver();
		clr.setDefaultLocale(Locale.US);
		clr.setCookieName("lang");
		clr.setCookieMaxAge(365 * 24 * 60 * 60);
		return clr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	/**
	 * Adds thymeleaf-layout-dialect support. 
	 */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    /**
     * Sets validator message source. 
     */
	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean factoryBean = ValidationAutoConfiguration.defaultValidator();
		factoryBean.setValidationMessageSource(messageSource);
		return factoryBean;
	}

}
