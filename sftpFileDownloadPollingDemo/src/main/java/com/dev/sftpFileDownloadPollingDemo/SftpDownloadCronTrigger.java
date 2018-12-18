package com.dev.sftpFileDownloadPollingDemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SftpDownloadCronTrigger {

	private static Logger LOGGER = LogManager.getLogger(SftpDownloadCronTrigger.class);

	@Value( "${sftp.polling.interval.in.minute}" )
	private int pollingIntervalInMinutes;

	@Value( "${cron.timing}" )
	private String cronTiming;

	@Value( "${cron.expression.enable}" )
	private boolean cronExpressionEnable;

	@Autowired
	SftpConnectorConfig sftpConnectorConfig;

	public void scheduleDownload() throws SchedulerException
	{
		JobDetail job = JobBuilder.newJob(SftpDownloadJob.class).withIdentity("SftpFileDownloadPollingJob", "group1")
				.build();

		job.getJobDataMap().put("sftpConnectorConfig", sftpConnectorConfig);

		Trigger trigger;

		if( cronExpressionEnable )
		{

			LOGGER.info("CronExpression is enabled, so using cron.timing property to schedule job..");
			trigger = TriggerBuilder.newTrigger().withIdentity("SftpFileDownloadPollingJobTrigger", "group1").startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule(cronTiming)).build();
		}
		else
		{
			LOGGER.info(
					"CronExpression is disabled, so using sftp.polling.interval.in.minute property to schedule job..");
			trigger = TriggerBuilder.newTrigger().withIdentity("SftpFileDownloadPollingJobTrigger", "group1").startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(pollingIntervalInMinutes)
							.repeatForever())
					.build();
		}

		// schedule it
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);

		LOGGER.info("Sftp File Download Polling Job Scheduled successfully....");

	}
}
