package hr.tpopovic.contentshare;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "content-share")
public record ContentShareProperties(
        String sendDirectory,
        String receiveDirectory,
        String baseUrl
) {
}
