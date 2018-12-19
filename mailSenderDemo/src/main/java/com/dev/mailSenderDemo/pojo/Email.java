package com.dev.mailSenderDemo.pojo;

public class Email {

	private String toEmail;
	private String emailSubjectLine;
	private String emailBody;

	public String getToEmail()
	{
		return toEmail;
	}

	public void setToEmail( String toEmail )
	{
		this.toEmail = toEmail;
	}

	public String getEmailSubjectLine()
	{
		return emailSubjectLine;
	}

	public void setEmailSubjectLine( String emailSubjectLine )
	{
		this.emailSubjectLine = emailSubjectLine;
	}

	public String getEmailBody()
	{
		return emailBody;
	}

	public void setEmailBody( String emailBody )
	{
		this.emailBody = emailBody;
	}

}
