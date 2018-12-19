package com.dev.mailSenderDemo.service;

import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import com.dev.mailSenderDemo.pojo.Email;

@Service
public class MailSenderService {

	private static final Logger logger = LogManager.getLogger(MailSenderService.class);

	@Autowired
	private JavaMailSender mailSender;

	@Value( "${mail.username}" )
	private String fromMail;

	public String sendMail( Email mailDetails )
	{
		try
		{
			MimeMessagePreparator preparator = getMessagePreparator(mailDetails);
			mailSender.send(preparator);
			return "Successfully email sent to: " + mailDetails.getToEmail();
		}
		catch( Exception e )
		{
			logger.error(e);
			return "Failed to send message to: " + mailDetails.getToEmail();
		}
	}

	private MimeMessagePreparator getMessagePreparator( Email mailDetails )
	{

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare( MimeMessage mimeMessage ) throws Exception
			{
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
				message.setTo(mailDetails.getToEmail());
				message.setFrom(fromMail);
				message.setSubject(mailDetails.getEmailSubjectLine());
				message.setText(mailDetails.getEmailBody(), true);
			}
		};
		return preparator;
	}

}
