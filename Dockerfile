FROM openjdk:17
LABEL verion="1.0.0"
COPY ./build/libs/notification-producer-system-0.0.1-SNAPSHOT.jar notification-producer-system-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","notification-producer-system-0.0.1-SNAPSHOT.jar"]