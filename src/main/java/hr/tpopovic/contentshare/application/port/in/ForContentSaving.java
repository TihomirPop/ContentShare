package hr.tpopovic.contentshare.application.port.in;

import hr.tpopovic.contentshare.application.domain.model.FileInTransit;

public interface ForContentSaving {

    ContentSaveResult save(FileInTransit fileInTransit);

}
