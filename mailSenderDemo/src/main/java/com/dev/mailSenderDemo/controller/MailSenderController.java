package com.dev.mailSenderDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.mailSenderDemo.pojo.Email;
import com.dev.mailSenderDemo.service.MailSenderService;

@RestController
@RequestMapping("/javamailservice")
public class MailSenderController {
	
	@Autowired
	MailSenderService mailSenderService;

	@PostMapping("/sendMail")
	public String sendMessage(@RequestBody Email mailDetails)
	{
		return mailSenderService.sendMail(mailDetails);
	}
}
