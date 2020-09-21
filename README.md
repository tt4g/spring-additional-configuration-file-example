# Overview

An example of adding an additional configuration file 
`spring-additional-configuration-file-example.yml` to Spring Boot configuration.

This configuration file will be loaded in Spring Boot applications with a higher
priority than `application.yml`.

In addition, can replace the configuration file itself with application arguments.

## Run

```bash
$ ./gradlew bootRun

$ curl -D - http://127.0.0.1:8080/showMessage
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 292

{
  "additional.message-ref": "Ref: This message defined in spring-additional-configuration-file-example.yml",
  "additional.message": "This message defined in spring-additional-configuration-file-example.yml"
}
```

Specify configuration file:

```bash
$ ./gradlew bootRun --args="--additional.config.path=${PWD}/additional-configuration.yml"

$ curl -D - http://127.0.0.1:8080/showMessage
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 252

{
  "additional.message-ref": "Ref: This message defined in additional-configuration.yml",
  "additional.message": "This message defined in additional-configuration.yml"
}
```

Cannot start application if configuration file does not exists.

```bash
$ ./gradlew bootRun --args="--additional.config.path=does-not-exists.yml"
```
