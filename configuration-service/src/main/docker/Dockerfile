#Start with base image containaing Java runtime
FROM openjdk:11-jdk-alpine
#Add maintener info
LABEL maintener="brice.mbiandji@g2snet.com"
#Adding a directory to container
RUN mkdir -p /usr/local/configuration
#Adding generated jar to configuration directory
ADD @project.build.finalName@.jar /usr/local/configuration/
ADD run.sh run.sh
ADD chmod +x run.sh
CMD ./run.sh
