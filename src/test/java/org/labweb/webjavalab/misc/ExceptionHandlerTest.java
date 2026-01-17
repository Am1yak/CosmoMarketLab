package org.labweb.webjavalab.misc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labweb.webjavalab.exceptions.CategoryNotFoundException;
import org.labweb.webjavalab.exceptions.GlobalExceptionHandler;
import org.labweb.webjavalab.exceptions.ProductNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerTest {
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void handleCategoryNotFoundException() {
        CategoryNotFoundException ex = new CategoryNotFoundException("Category not found");

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleCategoryNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Category not found", response.getBody().getTitle());
        assertEquals("/api/test", response.getBody().getProperties().get("path"));
    }

    @Test
    void handleProductNotFoundException() {
        ProductNotFoundException ex = new ProductNotFoundException("Product not found");

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleProductNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getBody().getTitle());
    }

    @Test
    void handleMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("dto", "name", "must not be blank");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleMethodArgumentNotValidException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Not a valid DTO", response.getBody().getTitle());
        assertTrue(response.getBody().getDetail().contains("must not be blank"));
    }
    
    @Test
    void handleConstraintViolationException() {
        ConstraintViolationException ex = new ConstraintViolationException("Constraint violation message", null);

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleConstraintViolationException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Not a valid path variable", response.getBody().getTitle());
    }

    @Test
    void handleHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("JSON parse error");

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleHttpMessageNotReadableException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Wrong type of JSON attribute", response.getBody().getTitle());
    }

    @Test
    void handleMethodArgumentTypeMismatchException() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getMessage()).thenReturn("Type mismatch error");

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleMethodArgumentTypeMismatchException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Not a valid type of path variable", response.getBody().getTitle());
    }

    @Test
    void handleException() {
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<ProblemDetail> response = globalExceptionHandler.handleException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody().getTitle());
        assertEquals("Unexpected error", response.getBody().getDetail());
    }
}
