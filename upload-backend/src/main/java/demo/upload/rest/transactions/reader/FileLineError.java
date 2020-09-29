package demo.upload.rest.transactions.reader;

import java.util.List;

public class FileLineError {

    private long lineNumber;
    private List<String> errors;

    public FileLineError(long lineNumber, List<String> errors) {
        this.lineNumber = lineNumber;
        this.errors = errors;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public List<String> getErrors() {
        return errors;
    }
}
