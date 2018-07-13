FROM openjdk:8-jdk

COPY . /app
WORKDIR /app
RUN ./gradlew clean bootJar


FROM openjdk:8-jre

COPY --from=0 /app/build/libs/five9s-0.0.1-SNAPSHOT.jar /
WORKDIR /

CMD java -Xmx120m -jar five9s-0.0.1-SNAPSHOT.jar

