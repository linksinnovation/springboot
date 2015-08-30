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

### Validation In thymeleaf

    <div>
        Comment : <input type="text" th:field="*{comment}" /><br />
        <ul th:if="${#fields.hasErrors('comment')}">
            <li th:each="err : ${#fields.errors('comment')}" th:text="${err}">Input is incorrect</li>
        </ul>
        Author : <input type="text" th:field="*{author}" /><br />
        <ul th:if="${#fields.hasErrors('author')}">
            <li th:each="err : ${#fields.errors('author')}" th:text="${err}">Input is incorrect</li>
        </ul>
        <input type="submit" value="Save" />
    </div>

    @RequestMapping(method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("formComment") Comment comment, BindingResult result,Model model){
        if(result.hasErrors()){
            return "index";
        }

### REST controller IndexRestController.java

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

### Custom message validation
add ValidationMessages.properties

    com.linksinnovation.springboot.dto.comment.NotEmpty=comment cannot be empty

    public class Comment {
        @NotEmpty(message = "{com.linksinnovation.springboot.dto.comment.NotEmpty}")
        private String comment;


### Custom validator

    @Target({ElementType.FIELD,ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = StartWithConstraintValidator.class)
    public @interface StartWith {

        public String value() default "";

        public String message() default "Start with error";

        public Class<?>[] groups() default {};

        public Class<? extends Payload>[] payload() default {};
    }


    public class StartWithConstraintValidator implements ConstraintValidator<StartWith, String>{

        private String value;

        @Override
        public void initialize(StartWith a) {
            this.value = a.value();
        }

        @Override
        public boolean isValid(String t, ConstraintValidatorContext cvc) {
            return t.startsWith(value);
        }

    }


### ExceptionHandler

    @ControllerAdvice
    public class MethodArgumentNotValidExceptionHandler {

        @ResponseBody
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String, Object> handler(MethodArgumentNotValidException ex) {
            Map<String, Object> map = new HashMap<>();
            List<Object> list = new ArrayList<>();

            for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                Map<String, Object> mapError = new HashMap<>();
                mapError.put("field", error.getField());
                mapError.put("message", error.getDefaultMessage());
                list.add(mapError);
            }
            map.put("errors", list);
            return map;
        }
    }


## Day 2
### Add Spring data Dependency

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>

### Refactoring dto to domain and add annotation

    @Entity
    public class Comment {
        @Id
        @GeneratedValue
        private Integer id;

### Create Repository

    public interface CommentRepository extends JpaRepository<Comment, Integer>{

    }

http://docs.spring.io/spring-data/jpa/docs/1.4.3.RELEASE/reference/html/jpa.repositories.html

### update CommentService use Repository

    @Service
    public class CommentService {

        @Autowired
        private CommentRepository commentRepository;

        public List<Comment> get(){
            return commentRepository.findAll();
        }

        public List<Comment> save(Comment comment){
            commentRepository.save(comment);
            return commentRepository.findAll();
        }

    }

### update Comment add createDate

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @PrePersist
    private void insertCreateDate() {
        if (this.createDate == null) {
            this.createDate = new Date();
        }
    }

### change jackson date format

    spring.jackson.date-format=dd-MM-yyyy


### Switch to Mysql Database

add depencency

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.6</version>
    </dependency>

add mysql properties

    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/springboot
    spring.datasource.username=root
    spring.datasource.password=

### show qeury log

    spring.jpa.show_sql: true

### Change Data Definition Language

    #validate: validate the schema, makes no changes to the database.
    #update: update the schema.
    #create: creates the schema, destroying previous data.
    #create-drop: drop the schema at the end of the session.

    spring.jpa.hibernate.ddl-auto=update

### Add Spring Security dependency

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

### Add username password in config

    security.user.name=user
    security.user.password=password

### Create Self config for basic authen

    @Configuration
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
        }

        @Autowired
        protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser("user")
                    .password("password")
                    .roles("USER","ADMIN");
        }

    }

### Default form authen

        http
                .csrf()
                .disable()
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                .formLogin()
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();

### Custom from authen create index.html

    <!DOCTYPE html>
    <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
        <head>
            <title>Spring Security Example </title>
        </head>
        <body>
            <div th:if="${param.error}">
                Invalid username and password.
            </div>
            <div th:if="${param.logout}">
                You have been logged out.
            </div>
            <form th:action="@{/login}" method="post">
                <div><label> User Name : <input type="text" name="username"/> </label></div>
                <div><label> Password: <input type="password" name="password"/> </label></div>
                <div><input type="submit" value="Sign In"/></div>
            </form>
        </body>
    </html>

### Change security Config

        http
                .csrf()
                .disable()
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();


### Add viewController

    @Configuration
    public class MvcConfig extends WebMvcConfigurerAdapter{

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/login").setViewName("login");
        }

    }


### change from inmemory to database
create Userdetails model

    @Entity
    public class Userdetails implements UserDetails{

        @Id
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.EMPTY_LIST;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }  
    }

### Create UserDetailsRepository

    public interface UserDetailsRepository extends JpaRepository<Userdetails, String>{

    }

### Create UserDetailsService

    @Service
    public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

        @Autowired
        private UserDetailsRepository userDetailsRepository;

        @Override
        public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
            return userDetailsRepository.findOne(string);   
        }

    }


### Change config to use Userdetailsservice

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password("password")
//                .roles("USER","ADMIN");
    }

### Create User to database in file data.sql
INSERT INTO USERDETAILS(username,password) VALUES('user','password');

### Secure method

    @Configuration
    @EnableWebMvcSecurity
    @EnableGlobalMethodSecurity(securedEnabled = true,proxyTargetClass = true)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

then use @Secured at method in controller wanna secured it