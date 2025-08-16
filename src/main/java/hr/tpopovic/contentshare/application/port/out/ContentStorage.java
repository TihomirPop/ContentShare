package hr.tpopovic.contentshare.application.port.out;

import hr.tpopovic.contentshare.application.domain.model.FileInTransit;

public interface ContentStorage {

    ContentStoreResult store(FileInTransit fileInTransit);

}
