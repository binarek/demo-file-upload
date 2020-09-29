package demo.upload.rest.transactions.reader;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.*;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionFileReader {

    private final CsvFileDataLineValidator csvFileDataLineValidator;

    public TransactionFileReader(CsvFileDataLineValidator csvFileDataLineValidator) {
        this.csvFileDataLineValidator = csvFileDataLineValidator;
    }

    public List<FileLineError> readErrors(Reader fileReader) {
        var csvToBean = new CsvToBeanBuilder<CsvFileDataLine>(fileReader)
                .withType(CsvFileDataLine.class)
                .withMappingStrategy(new CsvFileMappingStrategy(csvFileDataLineValidator))
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

    private static class CsvFileMappingStrategy extends HeaderColumnNameMappingStrategy<CsvFileDataLine> {

        private final CsvFileDataLineValidator csvFileDataLineValidator;

        public CsvFileMappingStrategy(CsvFileDataLineValidator csvFileDataLineValidator) {
            this.csvFileDataLineValidator = csvFileDataLineValidator;
            setType(CsvFileDataLine.class);
        }

        @Override
        public CsvFileDataLine populateNewBean(String[] line) throws CsvDataTypeMismatchException, CsvConstraintViolationException, CsvRequiredFieldEmptyException, CsvValidationException {
            CsvFileDataLine dataLine = super.populateNewBean(line);
            var errors = csvFileDataLineValidator.validate(dataLine);
            if (!errors.isEmpty()) {
                throw new CsvFileDataLineException(errors);
            }
            return dataLine;
        }
    }
}
