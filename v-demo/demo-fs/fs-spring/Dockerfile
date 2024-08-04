FROM bitnami/java:17

WORKDIR /app

COPY file /var/vitamin/file
COPY ./build/libs/app.jar app.jar

EXPOSE 3000

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]