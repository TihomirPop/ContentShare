package hr.tpopovic.contentshare.application.port.out;

public sealed interface FileUploadResult {

    record Success() implements FileUploadResult {}

    record Failure() implements FileUploadResult {}
}
