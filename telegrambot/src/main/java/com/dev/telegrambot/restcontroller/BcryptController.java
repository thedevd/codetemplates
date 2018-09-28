package com.dev.telegrambot.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.telegrambot.pojo.User;
import com.dev.telegrambot.services.UserService;


@RestController
@RequestMapping("/bcrypt")
public class BcryptController {

	@Autowired
	UserService userService;
	
	@PostMapping("/add")
	public String addUser(@RequestBody User user)
	{
		return userService.addUser(user);
	}
	
	@PostMapping("/verify")
	public String verifyUser(@RequestBody User user)
	{
		return userService.verifyUser(user);
	}
	
}
