FROM maven:3.3-jdk-8
COPY . .
EXPOSE 8080
RUN mvn install
CMD ["java", "-jar", "target/banque-1.0.jar"]
