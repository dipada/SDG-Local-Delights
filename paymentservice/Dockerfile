FROM openjdk:21-jdk
LABEL authors="dan"
ARG JAR_FILE=target/*.jar
COPY ./target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app.jar"]