package hr.tpopovic.contentshare.application.port.out;

import hr.tpopovic.contentshare.application.domain.model.FileInTransit;

public interface ForFileUpload {

    FileUploadResult upload(FileInTransit fileInTransit);
}
