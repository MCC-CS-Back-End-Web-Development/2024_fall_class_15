FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY build/libs/tic-tac-toe-0.0.1-SNAPSHOT.jar tic-tac-toe.jar
EXPOSE 8082
CMD ["java", "-jar", "tic-tac-toe.jar"]