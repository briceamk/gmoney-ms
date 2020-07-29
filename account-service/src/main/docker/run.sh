#!/bin/sh
echo "Waiting for GMONEY Configuration service  to start"
echo "##################################################"

while ! $(nc -z configuration 7000 ); do sleep 3; done

echo "##################################################"
echo "Waiting for GMONEY Discovery service  to start"
echo "##################################################"

while ! $(nc -z discovery 7000 ); do sleep 3; done


echo "##################################################"
echo "Waiting for GMONEY Proxy service  to start"
echo "##################################################"

while ! $(nc -z rule 8100 ); do sleep 3; done

echo ">>>>>>>>>>>>>>>>> Starting GMONEY Account......."

echo "##################################################"
echo "GMONEY - Account Service"
echo "##################################################"
java -Dspring.profiles.active=$PROFILE -jar -Xmx32m -Xss256k /usr/local/account/@project.build.finalName@.jar