#!/bin/sh
echo "##################################################"
echo "Waiting for GMONEY Configuration service  to start"
echo "##################################################"

while ! $(nc -z configuration 7000 ); do sleep 3; done

echo ">>>>>>>>>>>>>>>>> Starting GMONEY Discovery......."

echo "##################################################"
echo "GMONEY - Discovery Service"
echo "##################################################"
java -Dspring.profiles.active=$PROFILE -jar -Xmx32m -Xss256k /usr/local/discovery/@project.build.finalName@.jar