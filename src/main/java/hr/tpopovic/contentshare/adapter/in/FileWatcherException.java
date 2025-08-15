package hr.tpopovic.contentshare.adapter.in;

import java.io.IOException;

public class FileWatcherException extends RuntimeException {

    public FileWatcherException(String message) {
        super(message);
    }

    public FileWatcherException(IOException e) {
        super(e);
    }

    public FileWatcherException(String message, InterruptedException e) {
        super(message, e);
    }

}
