package com.ardila.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RestStsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestStsApplication.class, args);
	}
	
	@RequestMapping("/greet")
	public String helloGreeting() {
		return "Hello REST";
	}
	
}
