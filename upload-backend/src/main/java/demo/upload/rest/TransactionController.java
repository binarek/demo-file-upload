package demo.upload.rest;

import demo.upload.domain.file.FileValidationException;
import demo.upload.domain.transaction.reader.TransactionFileReader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionFileReader transactionFileReader;

    public TransactionController(TransactionFileReader transactionFileReader) {
        this.transactionFileReader = transactionFileReader;
    }

    @PostMapping(value = "/upload")
    public void uploadTransactions(@RequestParam("file") MultipartFile file) throws IOException {
        validateIfIsCsvFile(file);
        try (var fileReader = new InputStreamReader(file.getInputStream())) {
            transactionFileReader.readTransactionsFromCsvFile(fileReader);
        }
    }

    private void validateIfIsCsvFile(MultipartFile file) {
        var contentType = file.getContentType();
        if (contentType != null && Stream.of("text/csv", "text/plain").noneMatch(contentType::equalsIgnoreCase)) {
            throw new FileValidationException("File is not a CSV");
        }
    }
}
