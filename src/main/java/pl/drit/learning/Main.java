package pl.drit.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(Main.class,args);
    }
}




