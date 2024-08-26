FROM openjdk:17

WORKDIR /app

COPY build/libs/back-0.0.1-SNAPSHOT.jar back.jar

LABEL authors="han"


CMD ["java", "-jar", "back.jar"]


#ENTRYPOINT ["top", "-b"]