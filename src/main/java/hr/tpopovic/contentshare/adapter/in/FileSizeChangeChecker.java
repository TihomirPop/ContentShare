package hr.tpopovic.contentshare.adapter.in;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

@Component
public class FileSizeChangeChecker {

    public void waitUntilStable(Path filePath, Duration waitInterval) {
        try {
            wait(filePath, waitInterval);
        } catch (IOException e) {
            throw new FileStreamException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new FileStreamException("Interrupted while waiting for file size stability", e);
        }
    }

    private static void wait(Path filePath, Duration waitInterval) throws IOException, InterruptedException {
        long previousSize = -1;
        int stableCount = 0;

        while(true) {
            long currentSize = Files.size(filePath);
            if (currentSize == previousSize) {
                stableCount++;
                if (stableCount >= 3) {
                    break;
                }
            } else {
                stableCount = 0;
                previousSize = currentSize;
            }
            Thread.sleep(waitInterval);
        }
    }

}
