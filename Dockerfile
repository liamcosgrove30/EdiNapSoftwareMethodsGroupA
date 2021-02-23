FROM openjdk:latest
# copy from source on our local machine to the docker image
COPY ./target/GroupASoftwareMethods-0.1.0.3-jar-with-dependencies.jar /tmp
# we want docker to execute the program in the working directory
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "GroupASoftwareMethods-0.1.0.3-jar-with-dependencies.jar"]
