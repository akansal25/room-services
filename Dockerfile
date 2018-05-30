FROM anapsix/alpine-java:latest

ADD target/room-services-*.jar /opt/room-services.jar

CMD ["java","-jar","/opt/room-services.jar"]

EXPOSE 8080