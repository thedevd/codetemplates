package com.dev.bcryptpasswordencoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.bcryptpasswordencoder.pojo.User;
import com.dev.bcryptpasswordencoder.service.UserService;

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
