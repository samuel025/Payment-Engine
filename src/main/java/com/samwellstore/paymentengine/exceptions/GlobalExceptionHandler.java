package com.samwellstore.paymentengine.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.samwellstore.paymentengine.dto.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        log.error("Resource not found exception: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of(
                "Resource Not Found",
                ex.getMessage(),
                getRequestPath(request),
                "RESOURCE_NOT_FOUND"
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(
            PaymentException ex, WebRequest request) {
        log.error("Payment exception: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Internal Server Error",
                ex.getMessage(),
                getRequestPath(request),
                "PAYMENT_ERROR"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleCustomAuthenticationException(
            AuthenticationException ex, WebRequest request) {
        log.error("Authentication exception: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of(
                "Authentication Error",
                ex.getMessage(),
                getRequestPath(request),
                "AUTHENTICATION_ERROR"
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    
    // Standard JPA exceptions
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException ex, WebRequest request) {
        log.error("Entity not found exception: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of(
                "Entity Not Found",
                ex.getMessage(),
                getRequestPath(request),
                "ENTITY_NOT_FOUND"
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Spring Security exceptions
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {
        log.error("Bad credentials exception: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of(
                "Invalid Credentials",
                "The provided credentials are invalid",
                getRequestPath(request),
                "INVALID_CREDENTIALS"
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, WebRequest request) {
        log.error("User already exists exception: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "User Already Exists",
                ex.getMessage(),
                getRequestPath(request),
                "USER_ALREADY_EXISTS"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RoleAccessDeniedException.class)
    public  ResponseEntity<ErrorResponse> handleRoleAccessDeniedException(
            RoleAccessDeniedException ex, WebRequest request
    ){
        log.error("Role access denied exception: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Role Access Denied",
                ex.getMessage(),
                getRequestPath(request),
                "ROLE_ACCESS_DENIED"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        log.error("Access denied exception: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.of(
                "Access Denied",
                "You do not have permission to access this resource",
                getRequestPath(request),
                "ACCESS_DENIED"
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    
    // Java standard exceptions
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument exception: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(
                "Invalid Argument",
                ex.getMessage(),
                getRequestPath(request),
                "INVALID_ARGUMENT"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    
    // Helper methods
    
    private String getRequestPath(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            return ((ServletWebRequest) request).getRequest().getRequestURI();
        }
        return "";
    }
}