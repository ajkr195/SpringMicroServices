package com.spring.boot.rocks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppUserWEBController {
	
	@GetMapping("/")
	public String homePage() {
		return "index";
	}

}
