package com.omertex.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan (basePackages = { "com.omertex.task.inquiry.service", "com.omertex.task.topic.service",
	"com.omertex.task.inquiry.attribute.service" })
@Import ({ WebAppContext.class, PersistenceContext.class })
@PropertySource ("classpath:application.properties")
public class TaskApplicationContext
{

    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer ()
    {
	return new PropertySourcesPlaceholderConfigurer ();
    }
}
