FROM bitnami/java:17

WORKDIR /app

COPY ./target/spring-jpa-mysql-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "app.jar"]