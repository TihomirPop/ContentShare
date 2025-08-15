package hr.tpopovic.contentshare.adapter.in;

import java.io.IOException;

public class FileStreamException extends RuntimeException {

    public FileStreamException(String message) {
        super(message);
    }

    public FileStreamException(IOException e) {
        super(e);
    }

}
