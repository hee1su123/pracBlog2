package spring.pracblog2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PracBlog2Application {

    public static void main(String[] args) {
        SpringApplication.run(PracBlog2Application.class, args);
    }

}
