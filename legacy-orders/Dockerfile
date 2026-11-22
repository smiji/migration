# Phase 0/1: Java 8 runtime. Phase 2: change to eclipse-temurin:17-jre
FROM eclipse-temurin:8-jre
WORKDIR /app
COPY target/legacy-orders-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-XX:+UseConcMarkSweepGC","-XX:MaxPermSize=256m","-jar","app.jar"]
