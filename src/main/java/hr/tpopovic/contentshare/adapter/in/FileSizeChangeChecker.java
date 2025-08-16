package hr.tpopovic.contentshare.adapter.in;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;

@Component
public class FileSizeChangeChecker {

    public Long waitUntilStable(Path filePath, Duration waitInterval) {
        waitUntilFileIsReadable(filePath, waitInterval);
        try {
            return wait(filePath, waitInterval);
        } catch (IOException e) {
            throw new FileStreamException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new FileStreamException("Interrupted while waiting for file size stability", e);
        }
    }

    private void waitUntilFileIsReadable(Path file, Duration waitInterval) {
        int maxRetries = 1000;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try (var _ = FileChannel.open(file, StandardOpenOption.READ)) {
                // Successfully opened = file is no longer locked
                return;
            } catch (IOException _) {
                // File still locked or being written
                retryCount++;
                try {
                    Thread.sleep(waitInterval);
                } catch (InterruptedException _) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        throw new FileStreamException("File is not readable after multiple attempts: " + file);
    }


    private Long wait(Path filePath, Duration waitInterval) throws IOException, InterruptedException {
        long previousSize = -1;
        int stableCount = 0;

        while(true) {
            long currentSize = Files.size(filePath);
            if (currentSize == previousSize) {
                stableCount++;
                if (stableCount >= 3) {
                    return currentSize;
                }
            } else {
                stableCount = 0;
                previousSize = currentSize;
            }
            Thread.sleep(waitInterval);
        }
    }

}
