package com.chaocodes.restapi;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableJSONDoc
public class Application
{
	public static void main(final String[] args) {
		final ApplicationContext ctx = SpringApplication.run(Application.class, args);
	}
}
