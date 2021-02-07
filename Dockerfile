FROM openjdk:latest
COPY ./target/GroupASoftwareMethods-0.1.0.1-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "GroupASoftwareMethods-0.1.0.1-jar-with-dependencies.jar"]