package com.dev.telegrambot.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotDetail {
	
	public static String botUsername;
	public static String botToken;
	public static Long chatId;
	
	@Value("${bot.name}")
	public void setBotUsername( String botUsername )
	{
		BotDetail.botUsername = botUsername;
	}
	
	@Value("${bot.token}")
	public void setBotToken( String botToken )
	{
		BotDetail.botToken = botToken;
	}
	
	@Value("${bot.chatid}")
	public void setChatId( String chatId )
	{
		BotDetail.chatId = Long.valueOf(chatId);
	}

	
}
