package hr.tpopovic.contentshare.adapter.out;

import hr.tpopovic.contentshare.ApiEndpoint;
import hr.tpopovic.contentshare.ContentShareProperties;
import hr.tpopovic.contentshare.CustomHeader;
import hr.tpopovic.contentshare.application.domain.model.FileInTransit;
import hr.tpopovic.contentshare.application.port.out.FileUploadResult;
import hr.tpopovic.contentshare.application.port.out.ForFileUpload;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class FileUploader implements ForFileUpload {

    private final CloseableHttpClient httpClient;
    private final URI uploadUrl;

    public FileUploader(ContentShareProperties properties) {
        this.httpClient = HttpClients.createDefault();
        this.uploadUrl = URI.create(properties.baseUrl())
                .resolve(ApiEndpoint.CONTENT.path());
    }

    @Override
    public FileUploadResult upload(FileInTransit fileInTransit) {
        HttpPost post = new HttpPost(uploadUrl);
        post.setHeader(CustomHeader.FILE_NAME.headerName(), fileInTransit.fileName());
        post.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        InputStreamEntity entity = new InputStreamEntity(
                fileInTransit.data(),
                fileInTransit.fileSize(),
                ContentType.APPLICATION_OCTET_STREAM
        );
        post.setEntity(entity);

        try {
            return httpClient.execute(post, response -> {
                int status = response.getCode();
                return (status >= 200 && status < 300)
                        ? new FileUploadResult.Success()
                        : new FileUploadResult.Failure();
            });
        } catch (IOException _) {
            return new FileUploadResult.Failure();
        }
    }
}
