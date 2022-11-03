FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} jongterest-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/jongterest-0.0.1-SNAPSHOT.jar"]
