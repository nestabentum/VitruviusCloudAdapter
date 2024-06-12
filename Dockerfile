FROM openjdk:21-jdk-slim

COPY target/adapter.jar /home/adapter.jar
COPY cloud-vsum /cloud-vsum

EXPOSE 8070

CMD [ "java", "-jar", "/home/adapter.jar" ]
# CMD [ "java","-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=0.0.0.0:8001", "-jar", "/home/adapter.jar" ]