# Dockerfile

> ref:  https://spring.io/guides/topicals/spring-boot-docker/ 

## Basic

```dockerfile
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
docker build -t myorg/myapp .
docker run -p 8080:8080 myorg/myapp
```

## Entry point

-  pass environment variables into the **java command**

  ```dockerfile
  FROM openjdk:8-jdk-alpine
  VOLUME /tmp
  ARG JAR_FILE=target/*.jar
  COPY ${JAR_FILE} app.jar
  ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]
  ```

  ```bash
  docker build -t myorg/myapp .
  docker run -p 8080:8080 -e "JAVA_OPTS=-Ddebug -Xmx128m" myorg/myapp
  ```
  > ``java -Ddebug -Xmx128m -jar /app.jar``
  >
  > Using an `ENTRYPOINT` with an explicit shell like the above means that you can pass environment variables into the java command, but you cannot provide command line arguments to the Spring Boot application. 

- pass environment variables into the **Spring Boot application**

  ```dockerfile
  FROM openjdk:8-jdk-alpine
  VOLUME /tmp
  ARG JAR_FILE=target/*.jar
  COPY ${JAR_FILE} app.jar
  ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]
  ```

  ```bash
  docker run -p 9000:9000 myorg/myapp --server.port=9000
  ```

  The use of `${0}` for the "command" (in this case the first program argument) and `${@}` for the "command arguments" (the rest of the program arguments) .

## Start your app quickly

-  Don’t use ``actuators`` if you can afford not to. 
-  Use Spring Boot 2.1+ and Spring 5+. 
-  Switch off JMX  by `spring.jmx.enabled=false` 
-  Run the JVM with `-noverify`. Also consider `-XX:TieredStopAtLevel=1` (will slow down the JIT later). 
-  Use the container memory hints for Java 8: `-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap` 

## Build Plugins

- Spotify Maven Plugin

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.spotify</groupId>
            <artifactId>dockerfile-maven-plugin</artifactId>
            <version>1.4.8</version>
            <configuration>
                <repository>myorg/${project.artifactId}</repository>
            </configuration>
        </plugin>
    </plugins>
</build>
```

```bash
mvn com.spotify:dockerfile-maven-plugin:build
```

This plugin will connect to ``localhost:2375 `` by default. In fact, it is not very useful.

## Continuous Integration

- [jenkins pipeline](https://jenkins.io/doc/book/pipeline/docker/ )

