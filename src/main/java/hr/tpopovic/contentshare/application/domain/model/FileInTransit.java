package hr.tpopovic.contentshare.application.domain.model;

import java.io.InputStream;

import static java.util.Objects.requireNonNull;

public record FileInTransit(
        InputStream data,
        String fileName,
        Long fileSize
) {

    public FileInTransit {
        requireNonNull(data, "InputStream cannot be null");
        requireNonNull(fileName, "File name cannot be null");
        if (fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be blank");
        }
        requireNonNull(fileSize, "File size cannot be null");
        if (fileSize < 0) {
            throw new IllegalArgumentException("File size cannot be negative");
        }
    }
}
