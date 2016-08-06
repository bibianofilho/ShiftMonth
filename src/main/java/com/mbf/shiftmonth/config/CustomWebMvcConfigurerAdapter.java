package com.mbf.shiftmonth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.mbf.shiftmonth.web.controller" })
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	
	 public CustomWebMvcConfigurerAdapter() {
	        super();
	    }

	@Override
	   public void addViewControllers(ViewControllerRegistry registry) {
	      super.addViewControllers(registry);
	      registry.addViewController("/").setViewName("index");	    
	   }
	 
	   @Bean
	   public ViewResolver viewResolver() {
	      InternalResourceViewResolver bean = new InternalResourceViewResolver();
	 
	      bean.setViewClass(JstlView.class);
	      bean.setPrefix("/WEB-INF/views/");
	      bean.setSuffix(".jsp");
	 
	      return bean;
	   }
	   
	   @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/web/**").addResourceLocations("/web/");
	    }
}
