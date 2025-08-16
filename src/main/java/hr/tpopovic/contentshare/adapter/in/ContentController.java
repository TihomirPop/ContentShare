package hr.tpopovic.contentshare.adapter.in;

import hr.tpopovic.contentshare.CustomHeader;
import hr.tpopovic.contentshare.application.domain.model.FileInTransit;
import hr.tpopovic.contentshare.application.port.in.ContentSaveResult;
import hr.tpopovic.contentshare.application.port.in.ForContentSaving;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ForContentSaving forContentSaving;

    public ContentController(ForContentSaving forContentSaving) {
        this.forContentSaving = forContentSaving;
    }

    @PostMapping(consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Void> receiveStream(HttpServletRequest request) throws IOException {
        String fileName = request.getHeader(CustomHeader.FILE_NAME.headerName());
        FileInTransit fileInTransit;
        try {
             fileInTransit = new FileInTransit(
                    request.getInputStream(),
                    fileName
            );
        } catch (NullPointerException | IllegalArgumentException _) {
            return ResponseEntity.badRequest().build();
        }

        ContentSaveResult result = forContentSaving.save(fileInTransit);

        return switch (result) {
            case ContentSaveResult.Success _ -> ResponseEntity.ok().build();
            case ContentSaveResult.Failure _ -> ResponseEntity.internalServerError().build();
        };
    }
}
