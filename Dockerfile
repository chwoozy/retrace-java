FROM openjdk:11.0.12
ADD target/sandbox.jar sandbox.jar
EXPOSE 8081
EXPOSE 80
COPY . .
ENTRYPOINT ["java","-jar", "sandbox.jar"]
