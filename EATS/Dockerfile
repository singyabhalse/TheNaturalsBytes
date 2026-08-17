FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/spring-boot-docker.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
