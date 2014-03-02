export JAVA_OPTS="${JAVA_OPTS} \
  -Deureka.enableSelfPreservation=false \
  -Xms512m \
  -Xmx1024m \
  -Dcom.sun.management.jmxremote=true \
  -Dcom.sun.management.jmxremote.port=7500 \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.ssl=false \
  -Djava.rmi.server.hostname=192.168.33.10
"
