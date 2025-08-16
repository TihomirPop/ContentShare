package hr.tpopovic.contentshare.application.domain.service;

import hr.tpopovic.contentshare.application.domain.model.FileInTransit;
import hr.tpopovic.contentshare.application.port.in.ContentSaveResult;
import hr.tpopovic.contentshare.application.port.in.ForContentSaving;
import hr.tpopovic.contentshare.application.port.out.ContentStorage;
import hr.tpopovic.contentshare.application.port.out.ContentStoreResult;

public class ContentSaver implements ForContentSaving {

    private final ContentStorage storage;

    public ContentSaver(ContentStorage storage) {
        this.storage = storage;
    }

    @Override
    public ContentSaveResult save(FileInTransit fileInTransit) {
        ContentStoreResult result = storage.store(fileInTransit);

        return switch (result) {
            case ContentStoreResult.Failure _ -> new ContentSaveResult.Failure();
            case ContentStoreResult.Success _ -> new ContentSaveResult.Success();
        };
    }
}
