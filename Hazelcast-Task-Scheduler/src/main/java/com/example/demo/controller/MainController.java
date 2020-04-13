package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
	
	@GetMapping("/welcome")
	public String hello() throws InterruptedException {
		
		System.out.println("Welcome from Server1");
		return "Welcome from Server1";
	}

}
