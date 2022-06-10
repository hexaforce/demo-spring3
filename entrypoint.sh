#!/bin/sh 

#=========================================
## springboot start
java -Duser.timezone=JST -jar app.jar

#=========================================
## springboot start (JMX debug)
# java -Duser.timezone=JST \
#      -Djava.rmi.server.hostname=localhost \
#      -Dcom.sun.management.jmxremote=true \
#      -Dcom.sun.management.jmxremote.port=3968 \
#      -Dcom.sun.management.jmxremote.rmi.port=3968 \
#      -Dcom.sun.management.jmxremote.ssl=false \
#      -Dcom.sun.management.jmxremote.local.only=false \
#      -Dcom.sun.management.jmxremote.authenticate=false \
#      -jar app.jar
