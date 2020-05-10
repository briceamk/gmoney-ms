#!/bin/sh

echo "##################################################"
echo "Configuration Service"
echo "##################################################"
java -Dspring.profiles.active=$PROFILE -jar -Xmx32m -Xss256k /usr/local/discovery/discovery-service-0.0.1-SNAPSHOT.jar