package jpchs.spring_app;

import org.jspecify.annotations.NullMarked;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringAppApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringAppApplication.class, args);
    }

    @Override
    @NullMarked
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringAppApplication.class);
    }
}
