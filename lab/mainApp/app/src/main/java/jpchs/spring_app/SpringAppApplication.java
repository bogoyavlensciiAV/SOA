package jpchs.spring_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistration;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SpringAppApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringAppApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringAppApplication.class);
    }

    @Autowired(required = false)
    private ConsulAutoServiceRegistration registration;

    @EventListener(ApplicationReadyEvent.class)
    public void check() {
        registration.start();
    }
}
