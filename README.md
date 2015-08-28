#Spring Boot Application

##Create Maven project from Netbeans
    File -> New Project -> Categories = Maven -> Project = Java Application -> Next
    Project Name = springboot -> Finish

##Add Spring Boot Dependency to pom.xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.2.5.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>

##Create Main Class Application.java

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

## Create First Controller IndexController.java

@Controller
@RequestMapping("/")
public class IndexController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String get(){
        return "index";
    }
}


## Add template engine Dependency in pom.xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

## Add first html template in src/main/resources/templates/index.html

<!DOCTYPE html>
<html>
    <head>
        <title>Spring Boot</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    </head>
    <body>
        <div>write content</div>
    </body>
</html>

## Run it

mvn spring-boot:run

## Make template reload when change add /src/main/resources/application.properties

spring.thymeleaf.cache=false