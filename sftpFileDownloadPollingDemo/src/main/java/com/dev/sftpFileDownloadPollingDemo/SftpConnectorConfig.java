package com.dev.sftpFileDownloadPollingDemo;

import java.util.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Component
public class SftpConnectorConfig {

	private static Logger LOGGER = LogManager.getLogger(SftpConnectorConfig.class);

	@Value( "${sftp.host}" )
	private String sftpHost;

	@Value( "${sftp.port}" )
	private int sftpPort;

	@Value( "${sftp.user}" )
	private String sftpUser;

	@Value( "${sftp.password}" )
	private String sftpPasword;

	@Value( "${sftp.remote.directory.download}" )
	private String sftpRemoteDirectoryDownload;

	@Value( "${local.directory.download}" )
	private String localDirectoryDownload;

	@Value( "${cron.timing}" )
	private String cronTiming;

	@Value( "${sftp.remote.file.name}" )
	private String fileName;

	private Session session = null;
	private ChannelSftp sftpChannel = null;

	public void connectSftpServer()
	{
		LOGGER.info("Establishing connection to sftpServer: " + sftpHost + " at port: " + sftpPort);
		JSch jsch = new JSch();
		try
		{
			session = jsch.getSession(sftpUser, sftpHost, sftpPort);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(sftpPasword);
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();
			sftpChannel = (ChannelSftp) channel;

			LOGGER.info("Connection Established to sftpServer: " + sftpHost);
		}
		catch( JSchException e )
		{
			LOGGER.error("Failed to connect sftpServer: " + sftpHost, e);
		}
	}

	public void downloadFile()
	{
		try
		{
			@SuppressWarnings( "unchecked" )
			Vector<ChannelSftp.LsEntry> list = sftpChannel.ls(sftpRemoteDirectoryDownload);

			for( ChannelSftp.LsEntry entry : list )
			{
				if( entry.getFilename().equalsIgnoreCase(fileName) )
				{
					sftpChannel.get(sftpRemoteDirectoryDownload + "/" + entry.getFilename(), localDirectoryDownload);
					LOGGER.info("###### File " + entry.getFilename() + " Downloaded to localDir: "
							+ localDirectoryDownload);
					break;
				}
			}

		}
		catch( SftpException e )
		{
			LOGGER.error("Failed to download remote File: " + sftpRemoteDirectoryDownload + "/" + fileName
					+ " to localDir: " + localDirectoryDownload, e);
		}
		finally
		{
			closeConnection();
		}
	}

	private void closeConnection()
	{
		if( session.isConnected() != false )
		{
			sftpChannel.exit();
			session.disconnect();
			LOGGER.info("sftp Connection to host: " + sftpHost + " has been closed");
		}

	}

}
