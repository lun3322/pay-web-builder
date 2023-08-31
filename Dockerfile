FROM ibm-semeru-runtimes:open-17-jre-focal
WORKDIR /app
COPY pay-web/target/pay-web-3.2.0.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
