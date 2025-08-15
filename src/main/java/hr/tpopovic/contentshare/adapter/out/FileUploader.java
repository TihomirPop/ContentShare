package hr.tpopovic.contentshare.adapter.out;

import hr.tpopovic.contentshare.application.domain.model.FileInTransit;
import hr.tpopovic.contentshare.application.port.out.FileUploadResult;
import hr.tpopovic.contentshare.application.port.out.ForFileUpload;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;


@Component
public class FileUploader implements ForFileUpload {

    private final RestTemplate restTemplate;
    private final URI uploadUrl;

    public FileUploader(FileUploaderProperties properties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate = new RestTemplate(requestFactory);
        this.uploadUrl = URI.create(properties.uploadUrl());
    }

    @Override
    public FileUploadResult upload(FileInTransit fileInTransit) {
        String status = restTemplate.execute(
                uploadUrl,
                HttpMethod.POST,
                request -> request(fileInTransit, request),
                FileUploader::response
        );

        return switch (status) {
            case String s when s.startsWith("2") -> new FileUploadResult.Success();
            case null, default -> new FileUploadResult.Failure();
        };
    }

    private static void request(FileInTransit fileInTransit, ClientHttpRequest request) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.set("X-File-Name", fileInTransit.fileName());
        try (InputStream in = fileInTransit.data(); OutputStream out = request.getBody()) {
            in.transferTo(out);
        }
    }

    private static String response(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().toString();
    }

}
