package hr.tpopovic.contentshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ContentShareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentShareApplication.class, args);
    }

}
