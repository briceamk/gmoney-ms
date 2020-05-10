#!/bin/sh

echo "##################################################"
echo "GMONEY - Discovery Service"
echo "##################################################"
java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar -Xmx32m -Xss256k /usr/local/discovery/discovery-service-0.0.1-SNAPSHOT.jar