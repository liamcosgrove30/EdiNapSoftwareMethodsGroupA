FROM openjdk:latest
COPY ./target/classes/com/napier/GroupA
WORKDIR /tmp
ENTRYPOINT ["java", "com.napier.GroupA.App"]