package hr.tpopovic.contentshare.adapter.in;

import hr.tpopovic.contentshare.application.domain.model.FileInTransit;
import hr.tpopovic.contentshare.application.port.out.FileUploadResult;
import hr.tpopovic.contentshare.application.port.out.ForFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class LocalFileStreamer {

    private final ForFileUpload forFileUpload;
    private final FileSizeChangeChecker changeChecker;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final Logger log = LoggerFactory.getLogger(LocalFileStreamer.class);

    public LocalFileStreamer(ForFileUpload forFileUpload, FileSizeChangeChecker changeChecker) {
        this.forFileUpload = forFileUpload;
        this.changeChecker = changeChecker;
    }

    public void streamToRemote(Path filePath) {
        executorService.submit(() -> streamFile(filePath));
    }

    private void streamFile(Path filePath) {
        if (!Files.exists(filePath)) {
            throw new FileStreamException("File does not exist: " + filePath);
        }

        changeChecker.waitUntilStable(filePath, Duration.ofMillis(500));

        try(InputStream inputStream = Files.newInputStream(filePath)) {
            String fileName = filePath.getFileName().toString();
            FileInTransit fileInTransit = new FileInTransit(inputStream, fileName);
            FileUploadResult result = forFileUpload.upload(fileInTransit);

            switch (result) {
                case FileUploadResult.Failure _ -> log.error("Failed to upload file: {}", fileName);
                case FileUploadResult.Success _ -> log.info("Successfully uploaded file: {}", fileName);
            }
        } catch (IOException e) {
            throw new FileStreamException(e);
        }
    }

}
