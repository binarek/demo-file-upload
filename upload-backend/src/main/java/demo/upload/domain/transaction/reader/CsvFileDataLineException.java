package demo.upload.domain.transaction.reader;

import com.opencsv.exceptions.CsvValidationException;

import java.util.List;

class CsvFileDataLineException extends CsvValidationException {

    private final List<String> errors;

    CsvFileDataLineException(List<String> errors) {
        this.errors = errors;
    }

    List<String> getErrors() {
        return errors;
    }
}
