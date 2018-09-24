package com.dev.bcryptpasswordencoder.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.bcryptpasswordencoder.pojo.User;
import com.dev.bcryptpasswordencoder.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	
	PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public String addUser( User user)
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
	
	public String verifyUser( User user)
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
