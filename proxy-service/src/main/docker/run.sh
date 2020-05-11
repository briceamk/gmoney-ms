#!/bin/sh
echo "##################################################"
echo "Waiting for GMONEY Configuration service  to start"
echo "##################################################"

while ! $(nc -z configuration 7000 ); do sleep 3; done

echo "##################################################"
echo "Waiting for GMONEY Discovery Service to start"
echo "##################################################"

while ! $(nc -z discovery 7010 ); do sleep 3; done

echo ">>>>>>>>>>>>>>>>>>>>> Starting GMONEY Proxy......."

echo "##################################################"
echo "GMONEY - Proxy Service"
echo "##################################################"
java -Dspring.profiles.active=$PROFILE -jar -Xmx32m -Xss256k /usr/local/proxy/@project.build.finalName@.jar