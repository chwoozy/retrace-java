FROM maven:3.6-jdk-11 as maven_build
WORKDIR /app

#copy directories
COPY . .

RUN mvn clean install -U

ADD target/sandbox.jar sandbox.jar

EXPOSE 8081

ENTRYPOINT ["java", "-DSTACKIFY_APPLICATION_NAME=JavaSandboxApp", "-DSTACKIFY_ENVIRONMENT_NAME=SandboxTest", "-javaagent:/usr/local/stackify/stackify-java-apm/stackify-java-apm.jar", "-jar", "sandbox.jar"]
