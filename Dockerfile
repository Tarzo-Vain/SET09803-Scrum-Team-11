FROM openjdk:latest
COPY ./target/seMethods-0.1.0.1-jar-with-dependencies.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "seMethods-0.1.0.1-jar-with-dependencies.jar"]