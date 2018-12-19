### There was a requirement I encountered where I needed to download a jar file from SFTP location peridically to fetch always upto date jar and store it somewhere in my local machine.
So I have created a sample demo to demonstrate that scenario. (Although spring has something called sprint-boot-sftp-integration, but I have created my own utility)

External libraries I used here are (Please refer POM.xml) -
1. **Quartz Scheduler** - This is used to schedule the polling/download job at specified time interval, like periodically download the file for every 5 minute.\
2. **Jcraft Jsch** - This library, I have used to interact with SFTP server. Here interaction means making secure connection and download the file from sftp server.\

All the configurations to run this demo, have been specified in application.properties. Lets have a look at them once here- 
```
# local.directory.download - is the local location where file should be kept
##	ex- C:\\sftplocal\\myfiles
# sftp.host - host name of sftp server where files are located
# sftp.port - port of SFTP server
# sftp.user - Sftp server username
# sftp.password - Sftp User password
# sftp.remote.directory.download - Remote directory path on SFTP server where file is available.
##	ex- /home/thedevd
# sftp.remote.file.name - Name of the file with extension which you want to download periodically.
##	ex- sftpFileDownloadPollingDemo.jar
# sftp.polling.interval.in.minute - If cron.expression.enable is false then this time interval is used which is in minute
# cron.expression.enable - If true then cron expression is used to schedule sftp download job accordingly
# cron.timing - A cron time expression to download file from SFTP to local.directory.download
local.directory.download=C:\\sftplocal\\myfiles
sftp.host=<IP_ADDRESS_HERE>
sftp.port=22
sftp.user=<USERNAME_TO_LOGIN>
sftp.password=<PASSWORD_TO_LOGIN>
sftp.remote.directory.download=/home/thedevd
sftp.remote.file.name = sftpFileDownloadPollingDemo.jar
sftp.polling.interval.in.minute=5
cron.expression.enable=false
# Download file from sftp Every 5 minute using cron expression
cron.timing=0 */5 * ? * *
```

Description of the classes used in the demo -
1. **SftpConnectorConfig.java** - This bean component holds the above specified properties and logic to establish connection with SFTP server using jcraft jsch and download the required file.
2. **SftpDownloadJob.java** - This is actual quartz Job which needs to be invoked periodically. (The job of this class is - connecting to sftp server and download the specified file and at last release the conneciton)
3. **SftpDownloadCronTrigger.java** - This class is responsible to schedule a quartz scheduler to perform the required job which is specified in **SftpDownloadJob.java**
4. **SftpDownloadLauncher.java** - This is extra class whose task is to actually launch SftpDownloadCronTrigger to start quartz scheduler **SftpDownloadCronTrigger.java** 

So according to application.properties, after every 5 min the application is going to connect the server and download the file to local directory.
