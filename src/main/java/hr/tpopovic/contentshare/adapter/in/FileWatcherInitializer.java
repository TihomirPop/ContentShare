package hr.tpopovic.contentshare.adapter.in;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FileWatcherInitializer {

    private final FileWatcher fileWatcher;

    public FileWatcherInitializer(FileWatcher fileWatcher) {
        this.fileWatcher = fileWatcher;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initFileWatcher() {
        Thread.ofVirtual()
                .name("file-watcher-thread")
                .start(fileWatcher::initWatch);
    }

}
