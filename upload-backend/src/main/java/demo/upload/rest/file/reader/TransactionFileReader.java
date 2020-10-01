package demo.upload.rest.file.reader;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.*;
import demo.upload.rest.file.FileLineError;
import demo.upload.rest.file.TransactionCsvFileDataLine;
import demo.upload.rest.file.TransactionCsvFileDataLineValidator;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionFileReader {

    private final TransactionCsvFileDataLineValidator transactionCsvFileDataLineValidator;

    public TransactionFileReader(TransactionCsvFileDataLineValidator transactionCsvFileDataLineValidator) {
        this.transactionCsvFileDataLineValidator = transactionCsvFileDataLineValidator;
    }

    public List<FileLineError> readErrors(Reader fileReader) {
        var csvToBean = new CsvToBeanBuilder<TransactionCsvFileDataLine>(fileReader)
                .withType(TransactionCsvFileDataLine.class)
                .withMappingStrategy(new CsvFileMappingStrategy(transactionCsvFileDataLineValidator))
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(';')
                .withThrowExceptions(false)
                .build();

        csvToBean.parse();  // to process CSV lines, method result may be used to implement logic for correct data

        return csvToBean.getCapturedExceptions().stream()
                .map(this::mapToCsvFileLineError)
                .collect(Collectors.toList());
    }

    private FileLineError mapToCsvFileLineError(CsvException exception) {
        List<String> errors;
        if (exception instanceof CsvFileDataLineException) {
            errors = ((CsvFileDataLineException) exception).getErrors();
        } else {
            errors = List.of(exception.getLocalizedMessage());
        }
        return new FileLineError(exception.getLineNumber(), errors);
    }

    private static class CsvFileMappingStrategy extends HeaderColumnNameMappingStrategy<TransactionCsvFileDataLine> {

        private final TransactionCsvFileDataLineValidator transactionCsvFileDataLineValidator;

        public CsvFileMappingStrategy(TransactionCsvFileDataLineValidator transactionCsvFileDataLineValidator) {
            this.transactionCsvFileDataLineValidator = transactionCsvFileDataLineValidator;
            setType(TransactionCsvFileDataLine.class);
        }

        @Override
        public TransactionCsvFileDataLine populateNewBean(String[] line) throws CsvDataTypeMismatchException, CsvConstraintViolationException, CsvRequiredFieldEmptyException, CsvValidationException {
            TransactionCsvFileDataLine dataLine = super.populateNewBean(line);
            var errors = transactionCsvFileDataLineValidator.validate(dataLine);
            if (!errors.isEmpty()) {
                throw new CsvFileDataLineException(errors);
            }
            return dataLine;
        }
    }
}
