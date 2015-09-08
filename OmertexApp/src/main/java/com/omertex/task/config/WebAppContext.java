package com.omertex.task.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import com.omertex.task.config.viewresolver.JsonViewResolver;

@Configuration
@ComponentScan (basePackages = { "com.omertex.task.topic.controller", "com.omertex.task.inquiry.controller",
	"com.omertex.task.config.viewresolver" })
@EnableWebMvc
public class WebAppContext extends WebMvcConfigurerAdapter
{
    @Override
    public void configureContentNegotiation (ContentNegotiationConfigurer configurer)
    {
	configurer.ignoreAcceptHeader (true).defaultContentType (MediaType.APPLICATION_JSON);
    }


    @Bean
    public SimpleMappingExceptionResolver exceptionResolver ()
    {
	SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver ();

	Properties exceptionMappings = new Properties ();

	exceptionMappings.put ("java.lang.Exception", "error/error");
	exceptionMappings.put ("java.lang.RuntimeException", "error/error");

	exceptionResolver.setExceptionMappings (exceptionMappings);

	Properties statusCodes = new Properties ();

	statusCodes.put ("error/404", "404");
	statusCodes.put ("error/error", "500");

	exceptionResolver.setStatusCodes (statusCodes);

	return exceptionResolver;
    }


    @Bean
    public ViewResolver viewResolver (ContentNegotiationManager manager)
    {
	ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver ();
	viewResolver.setContentNegotiationManager (manager);

	List<ViewResolver> resolvers = new ArrayList<ViewResolver> ();
	resolvers.add (new JsonViewResolver ());

	viewResolver.setViewResolvers (resolvers);


	return viewResolver;
    }
}
