package hr.tpopovic.contentshare.adapter.out;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "content-share.file-uploader")
public record FileUploaderProperties(
        String uploadUrl
) {

}
