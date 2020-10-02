package demo.upload.domain.file;

import java.util.List;

public class FileValidationException extends RuntimeException {

    private final List<FileLineError> fileLineErrors;

    public FileValidationException(String message) {
        this(message, List.of());
    }

    public FileValidationException(String message, List<FileLineError> fileLineErrors) {
        super(message);
        this.fileLineErrors = fileLineErrors;
    }

    public List<FileLineError> getFileLineErrors() {
        return fileLineErrors;
    }
}
