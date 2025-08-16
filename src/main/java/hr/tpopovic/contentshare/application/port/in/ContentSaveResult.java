package hr.tpopovic.contentshare.application.port.in;

public sealed interface ContentSaveResult {

    record Success() implements ContentSaveResult {}

    record Failure() implements ContentSaveResult {}

}
