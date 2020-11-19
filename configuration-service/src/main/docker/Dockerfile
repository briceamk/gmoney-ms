#Start with base image containaing Java runtime
FROM adoptopenjdk/openjdk11:latest
#To run network command
RUN apt-get update -y
RUN apt-get install -y netcat
RUN apt-get install -y dos2unix
#Add maintener info
LABEL maintener="brice.mbiandji@g2snet.com"
#Adding a directory to container
RUN mkdir -p /usr/local/configuration
#Adding generated jar to configuration directory
ADD @project.build.finalName@.jar /usr/local/configuration/
ADD run.sh run.sh
RUN dos2unix run.sh
RUN chmod +x run.sh
CMD ./run.sh