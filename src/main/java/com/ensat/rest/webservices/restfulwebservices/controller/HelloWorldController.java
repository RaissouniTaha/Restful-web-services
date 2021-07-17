package com.ensat.rest.webservices.restfulwebservices.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ensat.rest.webservices.restfulwebservices.entity.HelloWorldBean;

@RestController
public class HelloWorldController {

	@Autowired
	MessageSource messageSource;
	//@RequestMapping(method = RequestMethod.GET,path = "hello-world")
	@GetMapping(path = "hello-world")
	public String HelloWorld() {
		return "Hello world";
	}
	@GetMapping(path = "hello-world-bean")
	public HelloWorldBean HelloWorldBean() {
		return new HelloWorldBean("Hello world");
	}
	@GetMapping(path = "hello-world/path-variable/{name}")
	public HelloWorldBean HelloWorldPathVariable(
			@PathVariable(name = "name") String name) {
		return new HelloWorldBean(String.format("Hello world %s",name));
	}
	@GetMapping(path = "hello-world-internationalization")
	public String HelloWorldInternationalized() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}
