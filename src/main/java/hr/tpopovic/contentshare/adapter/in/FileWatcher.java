package hr.tpopovic.contentshare.adapter.in;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Component
public class FileWatcher {

    private final Path pathToWatch;

    public FileWatcher(FileWatcherProperties properties) {
        this.pathToWatch = Path.of(properties.directory());
    }


    public void initWatch() {
        try(WatchService watchService = FileSystems.getDefault().newWatchService()) {
            pathToWatch.register(watchService, ENTRY_CREATE);

            while (true) {
                watchDirectory(watchService);
            }

        } catch (IOException e) {
            throw new FileWatcherException(e);
        }
    }

    private void watchDirectory(WatchService watchService) {
        WatchKey key = takeKey(watchService);

        key.pollEvents()
                .stream()
                .filter(event -> event.kind() == ENTRY_CREATE)
                .map(FileWatcher::castToPathEvent)
                .map(WatchEvent::context)
                .filter(path -> !path.toFile().isDirectory())
                .forEach(System.out::println); //todo: replace with actual processing logic

        resetKey(key);
    }

    private static WatchKey takeKey(WatchService watchService) {
        try {
            return watchService.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new FileWatcherException("Interrupted while waiting for watch key", e);
        }
    }

    private static WatchEvent<Path> castToPathEvent(WatchEvent<?> event) {
        @SuppressWarnings("unchecked")
        WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
        return pathEvent;
    }

    private static void resetKey(WatchKey key) {
        boolean valid = key.reset();
        if (!valid) {
            throw new FileWatcherException("Watch key is no longer valid.");
        }
    }

}
