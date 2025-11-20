package com.example.gestorTEDI.infrastructure.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    public AppExceptionHandler() {
        super();
        System.out.println("==============================================");
        System.out.println("====== AppExceptionHandler FOI CARREGADO ======");
        System.out.println("==============================================");
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        List<String> details = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(Objects::nonNull)
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return handleException(ex, "Validation Error", null, details, HttpStatus.BAD_REQUEST, request);
    }



    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request){
        ServletWebRequest webRequest = (ServletWebRequest)request;

        return handleException(ex, null, ex.getMessage(), null,  HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<Object> handleRequestException(RequestException ex, WebRequest request){
        ServletWebRequest webRequest = (ServletWebRequest)request;

        return handleException(ex, ex.getErrorCode(), ex.getMessage(), null, HttpStatus.BAD_REQUEST, request);
    }
    private ResponseEntity<Object> handleException(
            Exception ex,
            String errorCode,
            String message,
            List<String> details,
            HttpStatus status,
            WebRequest request){

        ServletWebRequest webRequest = (ServletWebRequest)request;
        return handleExceptionInternal(
                ex,
                RestError
                        .builder()
                        .errorCode(errorCode)
                        .errorMessage(message)
                        .details(details)
                        .status(status.value())
                        .path(webRequest.getRequest().getRequestURI())
                        .build(),
                new HttpHeaders(),
                status,
                request
        );

    }


}
