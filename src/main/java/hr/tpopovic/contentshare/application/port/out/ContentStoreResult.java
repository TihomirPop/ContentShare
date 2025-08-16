package hr.tpopovic.contentshare.application.port.out;

public sealed interface ContentStoreResult {

    record Success() implements ContentStoreResult {}
    record Failure() implements ContentStoreResult {}

}
