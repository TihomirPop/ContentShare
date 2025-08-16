package hr.tpopovic.contentshare.adapter.out;

import hr.tpopovic.contentshare.ContentShareProperties;
import hr.tpopovic.contentshare.application.domain.model.FileInTransit;
import hr.tpopovic.contentshare.application.port.out.ContentStorage;
import hr.tpopovic.contentshare.application.port.out.ContentStoreResult;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class LocalContentStorage implements ContentStorage {

    private final Path storagePath;

    public LocalContentStorage(ContentShareProperties properties) {
        storagePath = Path.of(properties.receiveDirectory());
    }

    @Override
    public ContentStoreResult store(FileInTransit fileInTransit) {
        String fileName = fileInTransit.fileName();
        Path tmpFilePath = storagePath.resolve(fileName + ".tmp");

        try(InputStream in = fileInTransit.data(); OutputStream out = Files.newOutputStream(tmpFilePath)) {
            in.transferTo(out);
            Path filePath = tmpFilePath.resolveSibling(fileName);
            Files.move(tmpFilePath, filePath);
            return new ContentStoreResult.Success();
        } catch (IOException _) {
            return new ContentStoreResult.Failure();
        }
    }

}
