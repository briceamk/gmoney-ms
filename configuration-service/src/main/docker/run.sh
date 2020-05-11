#!/bin/sh

echo "##################################################"
echo "GMONEY - Configuration Service"
echo "##################################################"
java -Dspring.profiles.active=$PROFILE -jar -Xmx32m -Xss256k /usr/local/configuration/@project.build.finalName@.jar