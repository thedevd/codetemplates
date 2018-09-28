package com.dev.telegrambot.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.dev.telegrambot.pojo.User;
import com.dev.telegrambot.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	@Autowired
	BotService botService;

	PasswordEncoder encoder = new BCryptPasswordEncoder();

	public String addUser( User user )
	{
		Optional<User> authenticatedUser = repository.findById(user.getUserEmail());
		if( authenticatedUser.isPresent() )
		{
			return "User with email: " + user.getUserEmail() + " already exist";
		}

		String bcryptedPassword = encoder.encode(user.getUserPassword());
		user.setUserPassword(bcryptedPassword);
		repository.save(user);

		botService.sendUserLoginActivity("User: " + user.getUserEmail() + " just signed up");

		return "User: " + user.getUserEmail() + " is added";
	}

	public String verifyUser( User user )
	{
		String message;
		Optional<User> authenticatedUser = repository.findById(user.getUserEmail());
		if( authenticatedUser.isPresent() )
		{
			boolean isPasswordMatched = encoder.matches(user.getUserPassword(),
					authenticatedUser.get().getUserPassword());
			if( isPasswordMatched )
			{
				message = "Password matches";
				botService.sendUserLoginActivity("Someone with userEmail: " + user.getUserEmail() + " just logged In");
			}
			else
			{
				message = "Password incorrect";
				botService.sendUserLoginActivity(
						"Someone with userEmail: " + user.getUserEmail() + " tried to login with wrong password!!");
			}
		}
		else
		{
			message = "userName does not exist";
		}
		return message;
	}

}
