package hr.tpopovic.contentshare.adapter.in;

public class FileStreamException extends RuntimeException {

    public FileStreamException(String message) {
        super(message);
    }

    public FileStreamException(Exception e) {
        super(e);
    }

    public FileStreamException(String message, Exception e) {
        super(message, e);
    }

}
