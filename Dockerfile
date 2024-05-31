FROM openjdk:17-jdk-slim

COPY server.jar /home/test.jar

CMD [ "java", "-jar", "/home/test.jar" ]