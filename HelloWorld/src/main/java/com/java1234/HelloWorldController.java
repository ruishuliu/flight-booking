package com.java1234;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@RequestMapping("/hello")
	public String fortest() {
		return "My first Spring Boot application, Oh yeah!";
	}
	
	@RequestMapping("/insert")
	public String insert(String content, Model model, HttpServletRequest request) {
		return "the content is:" + content;
		
	}
	

}
