package ke.pe.gbpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GbparkApplication {

    public static void main(String[] args) {
        SpringApplication.run(GbparkApplication.class, args);
    }

}
