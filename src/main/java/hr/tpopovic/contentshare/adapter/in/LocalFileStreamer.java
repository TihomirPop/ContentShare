package hr.tpopovic.contentshare.adapter.in;

import org.apache.tomcat.util.threads.VirtualThreadExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalFileStreamer {

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public void streamToRemote(Path filePath) {
        if (!Files.exists(filePath)) {
            throw new FileStreamException("File does not exist: " + filePath);
        }

        executorService.submit(() -> streamFile(filePath));
    }

    private void streamFile(Path filePath) {
        // todo: wait for file to be ready (finish copying...)
        try(InputStream inputStream = Files.newInputStream(filePath)) {

        } catch (IOException e) {
            throw new FileStreamException(e);
        }
    }

}
