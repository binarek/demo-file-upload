package demo.upload.transaction.file;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public abstract class FileLineError {

    @Value.Parameter
    public abstract long getLineNumber();

    @Value.Parameter
    public abstract List<String> getErrors();

    public static FileLineError of(long lineNumber, List<String> errors) {
        return ImmutableFileLineError.of(lineNumber, errors);
    }

//    private long lineNumber;
//    private List<String> errors;
//
//    public FileLineError(long lineNumber, List<String> errors) {
//        this.lineNumber = lineNumber;
//        this.errors = errors;
//    }
//
//    public long getLineNumber() {
//        return lineNumber;
//    }
//
//    public List<String> getErrors() {
//        return errors;
//    }
}
