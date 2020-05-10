#!/bin/sh

echo "##################################################"
echo "GMONEY - Proxy Service"
echo "##################################################"
java -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar -Xmx32m -Xss256k /usr/local/proxy/proxy-service-0.0.1-SNAPSHOT.jar