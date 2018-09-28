package com.dev.telegrambot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.dev.telegrambot.pojo.BotDetail;

public class TheDevDBot extends TelegramLongPollingBot {

	@Override
	public String getBotUsername()
	{
		return BotDetail.botUsername;
	}

	@Override
	public void onUpdateReceived( Update update )
	{
		System.out.println(update.getMessage().getChatId());
		SendMessage grpMessage = new SendMessage();
		grpMessage.setText("Message From Bot: " + update.getMessage().getText());
		grpMessage.setChatId(BotDetail.chatId);
		try
		{
			execute(grpMessage);
		}
		catch( TelegramApiException e )
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getBotToken()
	{
		return BotDetail.botToken;
	}

	public void sendUserLoginActivity( String message )
	{
		SendMessage telegramMsg = new SendMessage();
		telegramMsg.setText(message);
		telegramMsg.setChatId(BotDetail.chatId);

		try
		{
			execute(telegramMsg);
		}
		catch( TelegramApiException e )
		{
			throw new RuntimeException("Failed to send message to bot " + e);
		}
	}


}
