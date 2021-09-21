FROM gradle:7.2.0-jdk11 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app

RUN ./gradlew build --stacktrace 

FROM openjdk:11-jre-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /app/build/libs/helloworld-0.0.1-SNAPSHOT.war /app/helloworld-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/helloworld-0.0.1-SNAPSHOT.war"]