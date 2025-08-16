package hr.tpopovic.contentshare;

import hr.tpopovic.contentshare.application.domain.service.ContentSaver;
import hr.tpopovic.contentshare.application.port.in.ForContentSaving;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public ForContentSaving forContentSaving() {
        return new ContentSaver();
    }
}
