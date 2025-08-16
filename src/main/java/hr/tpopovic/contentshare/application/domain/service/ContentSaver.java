package hr.tpopovic.contentshare.application.domain.service;

import hr.tpopovic.contentshare.application.domain.model.FileInTransit;
import hr.tpopovic.contentshare.application.port.in.ContentSaveResult;
import hr.tpopovic.contentshare.application.port.in.ForContentSaving;

public class ContentSaver implements ForContentSaving {

    @Override
    public ContentSaveResult save(FileInTransit fileInTransit) {
        return null;
    }

}
