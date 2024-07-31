FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/common-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} back-jpa.jar
ENTRYPOINT ["java","-jar","/back-jpa.jar"]