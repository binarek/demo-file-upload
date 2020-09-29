package demo.upload.rest;

import demo.upload.rest.transactions.reader.FileLineError;
import demo.upload.rest.transactions.reader.TransactionFileReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionFileReader transactionFileReader;

    public TransactionController(TransactionFileReader transactionFileReader) {
        this.transactionFileReader = transactionFileReader;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<List<FileLineError>> uploadTransactions(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        try (var fileReader = new InputStreamReader(multipartFile.getInputStream())) {
            var errors = transactionFileReader.readErrors(fileReader);
            return errors.isEmpty() ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body(errors);
        }
    }
}
