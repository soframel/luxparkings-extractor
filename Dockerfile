FROM openjdk:13-ea-19-jdk-alpine3.9

#For systems with no cron
#RUN apt-get update && apt-get install -y --no-install-recommends cron && rm -rf /var/lib/apt/lists/*

COPY target/luxparkings-extractor-1.1-SNAPSHOT.jar /usr/share/luxparkings/luxparkings-extractor.jar
WORKDIR /usr/share/luxparkings/
#create script
RUN echo "java -Delastic.password=\${ELASTIC_PASSWORD} -jar /usr/share/luxparkings/luxparkings-extractor.jar" >> /usr/share/luxparkings/luxparkings
RUN chmod a+x /usr/share/luxparkings/luxparkings
#create file for crontab
RUN echo "*/5 * * * * /usr/share/luxparkings/luxparkings" >> /usr/share/luxparkings/luxparkings-cron
#apply correct permissions
RUN chmod 0600 /usr/share/luxparkings/luxparkings-cron
# Apply cron job
RUN crontab /usr/share/luxparkings/luxparkings-cron
#fix link-count, as cron is being a pain, and docker is making hardlink count >0 (very high)
#RUN touch /etc/crontab /etc/cron.*/*
# Create the log file to be able to run tail
RUN touch /var/log/cron.log
#run cron
#CMD cron && tail -f /var/log/cron.log
#CMD cron -f
CMD crond -l 2 -f