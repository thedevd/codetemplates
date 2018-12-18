package com.dev.sftpFileDownloadPollingDemo;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

@Service
public class SftpDownloadJob implements Job {

	private SftpConnectorConfig sftpConnectorConfig;

	public void execute( JobExecutionContext context ) throws JobExecutionException
	{

		// Get the sftpConnectorConfig object from Quartz jobContext, This is done as
		// @Autowire does not work directly in Quartz job
		JobDataMap dataMap = context.getMergedJobDataMap();
		sftpConnectorConfig = (SftpConnectorConfig) dataMap.get("sftpConnectorConfig");

		sftpConnectorConfig.connectSftpServer();
		sftpConnectorConfig.downloadFile();
	}
}
