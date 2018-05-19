FROM openjdk:8

CMD java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /room-services-0.0.1-SNAPSHOT.jar

EXPOSE 8102

ADD *.jar /room-services-0.0.1-SNAPSHOT.jar
