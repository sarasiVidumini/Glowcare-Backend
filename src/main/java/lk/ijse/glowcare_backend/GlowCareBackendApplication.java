package lk.ijse.glowcare_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class GlowCareBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlowCareBackendApplication.class, args);
    }

}
