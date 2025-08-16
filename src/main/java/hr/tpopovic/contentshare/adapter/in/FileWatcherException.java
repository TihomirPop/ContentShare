package hr.tpopovic.contentshare.adapter.in;

public class FileWatcherException extends RuntimeException {

    public FileWatcherException(String message) {
        super(message);
    }

    public FileWatcherException(Exception e) {
        super(e);
    }

    public FileWatcherException(String message, Exception e) {
        super(message, e);
    }

}
