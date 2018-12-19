### This is a spring-boot based web project which I have developed to demonstrate sending mail usecase using JavaMailSender API.

* **Dependency added to enable JavaMailSender API -**
```
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

* **Lets have a look at the [application.properties](https://github.com/thedevd/codetemplates/blob/master/mailSenderDemo/src/main/resources/application.properties) file, which contains configuration for SMPT server-**
```
# SMTP email configuration
###########################
# Important Node -
# mail.username - This is going to be fromEmail
# if mail.properties.mail.smtp.ssl.enable is set to true, then ssl will be used
# So make sure you give correct mail.port specific to ssl.  
mail.properties.mail.smtp.ssl.enable=true

mail.host=<SMTP_HOST_NAME>
mail.port=<SMTP_SERVER_PORT>
mail.username=<SMPT_USERNAME_EMAIL>
mail.password=<SMTP_USER_PASSWORD>
mail.properties.mail.smtp.auth=true
mail.properties.mail.transport.protocol=smtp
mail.properties.mail.smtp.ssl.trust=<SAME_AS_SMTP_HOST_NAME>
mail.properties.mail.smtp.starttls.enable=true
mail.properties.mail.debug=true
```
By default ssl is enabled in SMTP server. You can disable it by marking it false. 

* **Lets see the details of the classed used in the demo-**
1. [MailConfig.java](https://github.com/thedevd/codetemplates/blob/master/mailSenderDemo/src/main/java/com/dev/mailSenderDemo/configuration/MailConfig.java) - This Configuration class binds the properties defined in application.properties. And these properties are then used to initialize our JavaMailSender bean.
```
/*
 * JavaMailSender configuration.
 */
	@Bean
	public JavaMailSender javaMailService() {
	  JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setHost(mailHost);
		javaMailSender.setPort(mailPort);
		javaMailSender.setUsername(mailUsername);
		javaMailSender.setPassword(mailPassword);

		javaMailSender.setJavaMailProperties(getMailProperties());

		return javaMailSender;
	}
  ```
  2. [MailSenderService.java](https://github.com/thedevd/codetemplates/blob/master/mailSenderDemo/src/main/java/com/dev/mailSenderDemo/service/MailSenderService.java) - This @Service class is reposible to actually send the mail using JavaMailSender bean to specified recipient.
  ```
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
  ```
  
  * **How to run the demo-**
  1. First provide correct details of SMPT server in [application.properties](https://github.com/thedevd/codetemplates/blob/master/mailSenderDemo/src/main/resources/application.properties).
  2. Run the mail class [MailSenderDemoApplication.java](https://github.com/thedevd/codetemplates/blob/master/mailSenderDemo/src/main/java/com/dev/mailSenderDemo/MailSenderDemoApplication.java) as java application.
  3. Hit the http://localhost:8090/javamailservice/index on browser.
  4. Provide the recipient email id (Use your own email Id for testing purpose) and email's subject line plus body. And submit the form.
  5. Check if you recieved email. (you should recieve if everything goes well)
