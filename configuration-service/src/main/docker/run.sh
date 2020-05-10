#!/bin/sh

echo "##################################################"
echo "Configuration Service"
echo "##################################################"
java -Dspring.profiles.active=$PROFILE -jar -Xmx32m -Xss256k /usr/local/configuration/configuration-service-0.0.1-SNAPSHOT.jar