#!/bin/sh

echo "##################################################"
echo "GMONEY - Configuration Service"
echo "##################################################"
java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar -Xmx32m -Xss256k /usr/local/configuration/configuration-service-0.0.1-SNAPSHOT.jar