package demo.upload.rest.exception;

import demo.upload.domain.file.FileValidationException;
import demo.upload.domain.file.FileLineError;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.ThrowableProblem;

import java.util.List;

public class FileContentValidationProblem extends AbstractThrowableProblem {

    private final List<FileLineError> fileLineErrors;

    FileContentValidationProblem(ThrowableProblem problem, FileValidationException exception) {
        super(problem.getType(),
                problem.getTitle(),
                problem.getStatus(),
                problem.getDetail(), problem.getInstance(), problem.getCause(), problem.getParameters());
        this.fileLineErrors = exception.getFileLineErrors();
    }

    public List<FileLineError> getFileLineErrors() {
        return fileLineErrors;
    }
}
