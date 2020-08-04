#!/bin/sh
echo "Waiting for GMONEY Configuration service  to start"
echo "##################################################"

while ! $(nc -z configuration 7000 ); do sleep 3; done

echo "##################################################"
echo "Waiting for GMONEY Discovery service  to start"
echo "##################################################"

while ! $(nc -z discovery 7010 ); do sleep 3; done


echo "##################################################"
echo "Waiting for GMONEY Proxy service  to start"
echo "##################################################"

while ! $(nc -z proxy 8100 ); do sleep 3; done

echo "##################################################"
echo "Waiting for Postgres service  to start"
echo "##################################################"

while ! $(nc -z dbpostgresql 5838 ); do sleep 3; done


echo "##################################################"
echo "Waiting for Rabbit MQ service  to start"
echo "##################################################"

while ! $(nc -z gmoneyrabbitmq 5840 ); do sleep 3; done

echo ">>>>>>>>>>>>>>>>> Starting GMONEY Partner......."

echo "##################################################"
echo "GMONEY - Partner Service"
echo "##################################################"
java -Dspring.profiles.active=$PROFILE -jar -Xmx2048m -Xss256k /usr/local/partner/@project.build.finalName@.jar