package com.dev.bcryptpasswordencoder.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.bcryptpasswordencoder.pojo.User;
import com.dev.bcryptpasswordencoder.repository.DBRepository;

@RestController
@RequestMapping("/bcrypt")
public class BcryptController {

	@Autowired
	DBRepository repository;
	
	PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@PostMapping("/add")
	public String addUser(@RequestBody User user)
	{
		Optional<User> authenticatedUser = repository.findById(user.getUserEmail());
		if(authenticatedUser.isPresent())
		{
			return "User with email: " + user.getUserEmail() + " already exist";
		}
		String bcryptedPassword = encoder.encode(user.getUserPassword());
		user.setUserPassword(bcryptedPassword);
		repository.save(user);
		return "User: " + user.getUserEmail() + " is added";
	}
	
	@PostMapping("/verify")
	public String verifyUser(@RequestBody User user)
	{
		String message;
		Optional<User> authenticatedUser = repository.findById(user.getUserEmail());
		if(authenticatedUser.isPresent())
		{
			boolean isPasswordMatched = encoder.matches(user.getUserPassword(), authenticatedUser.get().getUserPassword());
			if(isPasswordMatched)
			{
				message = "Password matches";
			}
			else
			{
				message = "Password incorrect";
			}
		}
		else
		{
			message = "userName does not exist";
		}
		return message;
	}
	
}
