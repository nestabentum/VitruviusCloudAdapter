FROM openjdk:17-jdk-slim

COPY target/adapter.jar /home/adapter.jar
COPY cloud-vsum /cloud-vsum

CMD [ "java", "-jar", "/home/adapter.jar" ]