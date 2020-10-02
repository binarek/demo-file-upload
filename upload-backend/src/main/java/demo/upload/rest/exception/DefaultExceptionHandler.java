package demo.upload.rest.exception;

import demo.upload.domain.file.FileValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@RestControllerAdvice
public class DefaultExceptionHandler implements ProblemHandling {

    @ExceptionHandler(FileValidationException.class)
    public ResponseEntity<Problem> handleDomainException(FileValidationException exception, NativeWebRequest request) {
        return create(new FileContentValidationProblem(toProblem(exception, Status.BAD_REQUEST), exception), request);
    }
}
