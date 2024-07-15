FROM bitnami/java:17

WORKDIR /app

COPY ./build/libs/shortlink-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 3000

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
