package com.dlp.notification.exceptions;

import com.dlp.notification.dto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
        return handleExceptionInternal(ex, errorDto, headers, errorDto.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String localizedMessage = ex.getMostSpecificCause().getLocalizedMessage();
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, localizedMessage, "Enum not valid");
        return handleExceptionInternal(ex, errorDto, headers, errorDto.getStatus(), request);
    }

}