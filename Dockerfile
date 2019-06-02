FROM openjdk:13-ea-19-jdk-alpine3.9

COPY target/luxparkings-extractor-1.0-SNAPSHOT.jar /usr/share/luxparkings/luxparkings-extractor.jar
WORKDIR /usr/share/luxparkings/
#create script
RUN echo "java -Delastic.password=\${ELASTIC_PASSWORD} -jar /usr/share/luxparkings/luxparkings-extractor.jar" >> /usr/share/luxparkings/luxparkings.sh
RUN chmod a+x /usr/share/luxparkings/luxparkings.sh
#create file for crontab
RUN echo "*/5 * * * * /usr/share/luxparkings/luxparkings.sh" >> /usr/share/luxparkings/luxparkings-cron
# Apply cron job
RUN crontab /usr/share/luxparkings/luxparkings-cron
# Create the log file to be able to run tail
RUN touch /var/log/cron.log
# Run the command on container startup
# CMD crond && tail -f /var/log/cron.log
CMD crond -l 2 -f