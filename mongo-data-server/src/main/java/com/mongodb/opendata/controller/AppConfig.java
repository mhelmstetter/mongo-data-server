package com.mongodb.opendata.controller;

import java.util.List;

import net.eusashead.hateoas.converter.hal.HalHttpMessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.mongodb.Mongo;
import com.theoryinpractise.halbuilder.DefaultRepresentationFactory;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

@Configuration
public class AppConfig extends WebMvcConfigurationSupport {

	public @Bean MongoOperations mongoTemplate(Mongo mongo) {
		MongoTemplate mongoTemplate = new MongoTemplate(mongo, "test");
		return mongoTemplate;
	}

	/*
	 * Factory bean that creates the Mongo instance
	 */
	public @Bean MongoFactoryBean mongo() {
		MongoFactoryBean mongo = new MongoFactoryBean();
		mongo.setHost("localhost");
		return mongo;
	}

	/*
	 * Use this post processor to translate any MongoExceptions thrown in @Repository annotated classes
	 */
	public @Bean PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	
	   @Override
	    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	        // Add the HAL converter
	        converters.add(halConverter());
	    }

	    /** 
	     * Create a {@link HalHttpMessageConverter}
	     * using the {@link DefaultRepresentationFactory}
	     * @return
	    */
	    @Bean
	    public HttpMessageConverter<Object> halConverter() {
	        RepresentationFactory factory = new DefaultRepresentationFactory();
	        HalHttpMessageConverter converter = new HalHttpMessageConverter(factory);

	        // Wire any needed modules...

	        return converter;
	    }

}
