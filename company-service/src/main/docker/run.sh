#!/bin/sh
echo "##################################################"
echo "Waiting for GMONEY Configuration service  to start"
echo "##################################################"

while ! $(nc -z configuration 7000 ); do sleep 3; done

echo "##################################################"
echo "Waiting for GMONEY Discovery service  to start"
echo "##################################################"

while ! $(nc -z discovery 7000 ); do sleep 3; done

echo ">>>>>>>>>>>>>>>>> Starting GMONEY Company......."

echo "##################################################"
echo "GMONEY - Company Service"
echo "##################################################"
java -Dspring.profiles.active=$PROFILE -jar -Xmx32m -Xss256k /usr/local/company/@project.build.finalName@.jar