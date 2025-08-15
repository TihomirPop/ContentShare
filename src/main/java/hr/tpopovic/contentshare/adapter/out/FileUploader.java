package hr.tpopovic.contentshare.adapter.out;

import hr.tpopovic.contentshare.application.domain.model.FileInTransit;
import hr.tpopovic.contentshare.application.port.out.FileUploadResult;
import hr.tpopovic.contentshare.application.port.out.ForFileUpload;

public class FileUploader implements ForFileUpload {

    @Override
    public FileUploadResult upload(FileInTransit fileInTransit) {
        return null;
    }

}
