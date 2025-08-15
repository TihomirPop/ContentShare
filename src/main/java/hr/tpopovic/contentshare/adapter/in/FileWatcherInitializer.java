package hr.tpopovic.contentshare.adapter.in;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FileWatcherInitializer {

    private final InboundFileWatcher inboundFileWatcher;

    public FileWatcherInitializer(InboundFileWatcher inboundFileWatcher) {
        this.inboundFileWatcher = inboundFileWatcher;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initFileWatcher() {
        Thread.ofVirtual()
                .name("file-watcher-thread")
                .start(inboundFileWatcher::initWatch);
    }

}
