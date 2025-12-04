package uk.sky.jsdp.infrastructure.adapter.in.web.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import uk.sky.jsdp.domain.exception.BusinessException;
import uk.sky.jsdp.domain.exception.NotFoundException;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, NotFoundException.class})
    public ProblemDetail handleNotFound(RuntimeException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Resource not found");
        problem.setDetail(ex.getMessage());
        problem.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));

        log.warn(ex.getMessage(), ex);
        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleBadRequest(IllegalArgumentException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Invalid request data");
        problem.setDetail(ex.getMessage());
        problem.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));

        log.warn(ex.getMessage(), ex);
        return problem;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Data integrity violation");
        problem.setDetail("The provided data violates database rules.");
        problem.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        problem.setProperty("error", ex.getMostSpecificCause().getMessage());

        log.error(ex.getMessage(), ex);
        return problem;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusiness(BusinessException ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Business rule violation");
        problem.setDetail(ex.getMessage());
        problem.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));

        log.warn(ex.getMessage(), ex);
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Unexpected error");
        problem.setDetail("Contact support");
        problem.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        problem.setProperty("errorId", UUID.randomUUID().toString());

        log.error(ex.getMessage(), ex);
        return problem;
    }
}
