package hr.tpopovic.contentshare.adapter.in;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "content-share.file-watcher")
public record FileWatcherProperties(
        String directory
) {

}
