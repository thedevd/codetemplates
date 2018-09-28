package com.dev.telegrambot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.dev.telegrambot.bot.TheDevDBot;

public class TelegramBotRegistration {

	private static TheDevDBot telegramBot;
	
	public static void register()
	{
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try
		{
			telegramBot = new TheDevDBot();
			telegramBotsApi.registerBot(telegramBot);
		}
		catch( TelegramApiException e )
		{
			throw new RuntimeException("Failed to register bot " + e);
		}
	}

	public static TheDevDBot getInstance()
	{
		return telegramBot;
	}

}
