FROM openjdk:14
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} recipeApp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/recipeApp.jar"]