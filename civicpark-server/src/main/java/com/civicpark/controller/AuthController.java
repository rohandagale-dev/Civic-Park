package com.civicpark.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {


	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		return "hello world" + request.getSession().getId();
	}
	
	
}
