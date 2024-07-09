FROM openjdk:22-jdk

WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 1010

ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]