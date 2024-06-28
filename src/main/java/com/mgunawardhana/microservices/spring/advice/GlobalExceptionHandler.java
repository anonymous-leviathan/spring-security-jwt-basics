package com.mgunawardhana.microservices.spring.advice;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ProblemDetail handleNullPointerException(NullPointerException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setProperty("Error Reason", "Null Pointer Encountered!");
        log.error("ProblemDetails: {}, Null Pointer Exception Encountered!", problemDetail, e);
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setProperty("Error Reason", "Illegal Argument!");
        log.error("ProblemDetails: {}, Illegal Argument Exception Encountered!", problemDetail, e);
        return problemDetail;
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ProblemDetail handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setProperty("Error Reason", "Array Index Out Of Bounds!");
        log.error("ProblemDetails: {}, Array Index Out Of Bounds Exception Encountered!", problemDetail, e);
        return problemDetail;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ProblemDetail handleNumberFormatException(NumberFormatException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setProperty("Error Reason", "Number Format Exception!");
        log.error("ProblemDetails: {}, Number Format Exception Encountered!", problemDetail, e);
        return problemDetail;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleIllegalStateException(IllegalStateException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setProperty("Error Reason", "Illegal State!");
        log.error("ProblemDetails: {}, Illegal State Exception Encountered!", problemDetail, e);
        return problemDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty("Error Reason", "Bad Credentials!");
        log.error("ProblemDetails: {}, Bad Credentials Exception Encountered!", problemDetail, e);
        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        problemDetail.setProperty("Error Reason", "Access Denied!");
        log.error("ProblemDetails: {}, Access Denied Exception Encountered!", problemDetail, e);
        return problemDetail;
    }

    @ExceptionHandler(SignatureException.class)
    public ProblemDetail handleSignatureException(SignatureException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty("Error Reason", "Signature Exception!");
        log.error("ProblemDetails: {}, Signature Exception Encountered!", problemDetail, e);
        return problemDetail;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwtException(ExpiredJwtException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setProperty("Error Reason", "Expired JWT Exception!");
        log.error("ProblemDetails: {}, Expired JWT Exception Encountered!", problemDetail, e);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setProperty("Error Reason", "Unknown Exception!");
        log.error("ProblemDetails: {}, Unknown Exception Encountered!", problemDetail, e);
        return problemDetail;
    }
}