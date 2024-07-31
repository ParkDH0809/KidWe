FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/mongo-back-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} back-mongo.jar
ENTRYPOINT ["java","-jar","/back-mongo.jar"]