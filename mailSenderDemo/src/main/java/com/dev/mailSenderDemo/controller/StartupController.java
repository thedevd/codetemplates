package com.dev.mailSenderDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartupController {

	@GetMapping("/javamailservice/index")
	public String index()
	{
		return "index";
	}
	
}
