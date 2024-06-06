FROM openjdk:17-jdk-slim

COPY target/adapter.jar /home/adapter.jar
COPY cloud-vsum /cloud-vsum

EXPOSE 8070

CMD [ "java", "-jar", "/home/adapter.jar" ]