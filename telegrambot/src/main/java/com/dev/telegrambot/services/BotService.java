package com.dev.telegrambot.services;

import org.springframework.stereotype.Service;

import com.dev.telegrambot.TelegramBotRegistration;

@Service
public class BotService {
	
	public void sendUserLoginActivity(String message)
	{
		TelegramBotRegistration.getInstance().sendUserLoginActivity(message);
	}
	
}
