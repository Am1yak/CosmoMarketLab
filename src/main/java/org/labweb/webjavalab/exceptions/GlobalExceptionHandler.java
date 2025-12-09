package org.labweb.webjavalab.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleCategoryNotFoundException(CategoryNotFoundException ex,
                                                          HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Category not found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFoundException(ProductNotFoundException ex,
                                                                        HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Product not found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                               HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Not a valid DTO");

        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("Field '%s' %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        problemDetail.setDetail(detail);
        problemDetail.setProperty("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException ex,
                                                                            HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Not a valid path variable");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                                                                               HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Wrong type of JSON attribute");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
                                                                                   HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Not a valid type of path variable");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("path", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}
