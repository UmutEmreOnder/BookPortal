FROM openjdk:19-jdk-alpine3.16
ADD target/BookPortal-0.0.1-SNAPSHOT.jar BookPortal-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "BookPortal-0.0.1-SNAPSHOT.jar"]