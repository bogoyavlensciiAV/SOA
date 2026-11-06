package jpchs.isuApp.client;

import io.smallrye.stork.Stork;
import io.smallrye.stork.api.ServiceInstance;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;
import java.time.Duration;

@ApplicationScoped
public class StudyGroupClientFactory {

    private static final String SERVICE = "mainApp";

    @PostConstruct
    void init() {
        try {
            Stork.initialize();
        } catch (IllegalStateException ignored) {
            // уже инициализирован
        }
    }

    public StudyGroupClient create() {
        ServiceInstance si = Stork.getInstance()
                .getService(SERVICE)
                .selectInstance()
                .await().atMost(Duration.ofSeconds(5));

        String scheme = "https";
        URI uri = URI.create(scheme + "://" + si.getHost() + ":" + si.getPort());

        System.out.println("=== STORK DEBUG ===");
        System.out.println("Host: " + si.getHost());
        System.out.println("Port: " + si.getPort());
        System.out.println("Secure: " + si.isSecure());
        System.out.println("Final URL: " + uri);
        System.out.println("===================");

        return RestClientBuilder.newBuilder()
                .baseUri(uri)
                .build(StudyGroupClient.class);
    }
}