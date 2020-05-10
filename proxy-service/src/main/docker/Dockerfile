#Start with base image containaing Java runtime
FROM adoptopenjdk/openjdk11:latest
#Add maintener info
LABEL maintener="brice.mbiandji@g2snet.com"
#Adding a directory to container
RUN mkdir -p /usr/local/configuration
#Adding generated jar to configuration directory
ADD @project.build.finalName@.jar /usr/local/proxy/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
