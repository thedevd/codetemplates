package com.dev.sftpFileDownloadPollingDemo;

import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SftpDownloadLauncher {

	private static Logger LOGGER = LogManager.getLogger(SftpDownloadLauncher.class);

	@Autowired
	SftpDownloadCronTrigger jobTrigger;

	@PostConstruct
	public void init()
	{
		try
		{
			jobTrigger.scheduleDownload();
		}
		catch( SchedulerException e )
		{
			LOGGER.error(e);
		}
	}

}
