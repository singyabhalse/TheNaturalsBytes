FROM openjdk:21
EXPOSE 8082
WORKDIR /app

ADD target/spring-boot-docker.jar /app/spring-boot-docker.jar 
ENTRYPOINT ["java","-jar","/app/spring-boot-docker.jar"]