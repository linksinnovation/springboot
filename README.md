#Spring Boot Application

###Create Maven project from Netbeans
    File -> New Project -> Categories = Maven -> Project = Java Application -> Next
    Project Name = springboot -> Finish

###Add Spring Boot Dependency to pom.xml
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

###Create Main Class Application.java

    @SpringBootApplication
    public class Application {
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }

### Create First Controller IndexController.java

    @Controller
    @RequestMapping("/")
    public class IndexController {

        @RequestMapping(method = RequestMethod.GET)
        public String get(){
            return "index";
        }
    }


### Add template engine Dependency in pom.xml

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

### Add first html template
add src/main/resources/templates/index.html

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

### First Run

    mvn spring-boot:run

### Make template reload when change
add /src/main/resources/application.properties

    spring.thymeleaf.cache=false

### Thymeleaf expression

    Simple expressions:
    Variable Expressions: ${...}
    Selection Variable Expressions: *{...}
    Message Expressions: #{...}
    Link URL Expressions: @{...}

http://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html#standard-expression-syntax

### Add xml namespace to index.html

    <!DOCTYPE html SYSTEM "http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd">

    <html xmlns="http://www.w3.org/1999/xhtml"
          xmlns:th="http://www.thymeleaf.org">

### Create Form

    <div th:each="comment : ${comments}" style="border: 1px solid #333;margin: 5px;width:500px;">
        <p th:text="${comment.comment}"></p>
        <p style="text-align: right" th:utext="'Write by '+${comment.author}"></p>
    </div>

    <form action="#" th:action="@{/}" th:object="${formComment}" method="post">
        <div>
            Comment : <input type="text" th:field="*{comment}" />
            Author : <input type="text" th:field="*{author}" />
            <input type="submit" value="Save" />
        </div>
    </form>

### Create DTO

    public class Comment {
        private String comment;
        private String author;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }


    }

### Create Service

@Service
public class CommentService {
    
    private static List<Comment> comments = new ArrayList<>();
    
    public List<Comment> get(){
        return comments;
    }
    
    public List<Comment> save(Comment comment){
        comments.add(comment);
        return comments;
    }
    
}

### update IndexController

    @Autowired
    private CommentService commentService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String get(Model model){
        model.addAttribute("comments", commentService.get());
        model.addAttribute("formComment", new Comment());
        return "index";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String save(@ModelAttribute("formComment") Comment comment,Model model){
        List<Comment> save = commentService.save(comment);
        model.addAttribute("comments", save);
        model.addAttribute("formComment", new Comment());
        return "index";
    }

### REST controller

    @RestController
    @RequestMapping("/rest")
    public class IndexRestController {

        @Autowired
        private CommentService commentService;

        @RequestMapping(method = RequestMethod.GET)
        public List<Comment> get(){
            return commentService.get();
        }

        @RequestMapping(method = RequestMethod.POST)
        public List<Comment> save(@RequestBody Comment comment){
            return commentService.save(comment);
        }

    }

### JSR 303 Bean validation

    public class Comment {
        private String comment;
        @NotEmpty
        private String author;


    @RequestMapping(method = RequestMethod.POST)
    public List<Comment> save(@Valid @RequestBody Comment comment){
        return commentService.save(comment);
    }