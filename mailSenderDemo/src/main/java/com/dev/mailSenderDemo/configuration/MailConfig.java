package com.dev.mailSenderDemo.configuration;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan
public class MailConfig {

	@Value("${mail.host}")
	private String mailHost;

	@Value("${mail.port}")
	private Integer mailPort;

	@Value("${mail.username}")
	private String mailUsername;

	@Value("${mail.password}")
	private String mailPassword;

	@Value("${mail.properties.mail.transport.protocol}")
	private String mailTransportProtocol;

	@Value("${mail.properties.mail.smtp.auth}")
	private String mailSmtpAuth;

	@Value("${mail.properties.mail.smtp.ssl.trust}")
	private String mailSmtpSslTrust;

	@Value("${mail.properties.mail.smtp.starttls.enable}")
	private String mailSmtpStarttlsEnable;

	@Value("${mail.properties.mail.debug}")
	private String mailDebug;

	@Value("${mail.properties.mail.smtp.ssl.enable}")
	private Boolean mailSmtpSslEnable;

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

	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", mailTransportProtocol);
		properties.setProperty("mail.smtp.auth", mailSmtpAuth);
		properties.setProperty("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
		properties.setProperty("mail.smtp.ssl.trust", mailSmtpSslTrust);
		properties.setProperty("mail.debug", mailDebug);

		if (mailSmtpSslEnable) {
			// Use the following isSSL enabled
			properties.put("mail.smtp.socketFactory.port", mailPort);
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			properties.put("mail.smtp.ssl.enable", mailSmtpSslEnable);
		}

		return properties;
	}

}
