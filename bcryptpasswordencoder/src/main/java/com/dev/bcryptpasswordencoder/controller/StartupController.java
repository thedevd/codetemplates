package com.dev.bcryptpasswordencoder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartupController {

	@GetMapping("/bcrypt/index")
	public String index()
	{
		return "index";
	}
	
}
