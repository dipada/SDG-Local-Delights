FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]