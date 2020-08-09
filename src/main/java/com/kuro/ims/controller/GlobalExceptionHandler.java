package com.kuro.ims.controller;

import com.kuro.ims.dto.Response;
import com.kuro.ims.exception.ImsClientException;
import com.kuro.ims.exception.ImsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler({ImsException.class})
    public ResponseEntity<Response<?>> errorHandler(ImsException e)
    {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e instanceof ImsClientException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response.builder()
                .message(e.getMessage())
                .build());
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response<?>> errorHandler(Exception e)
    {
        log.error(null, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Response.builder()
                .message("An error occurred while performing the operation, please try again")
                .build());
    }


    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Response<?>> errorHandler(AccessDeniedException e)
    {
        log.error(null, e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Response.builder()
                .message("You are not authorized to perform this operation")
                .build());
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Response<?>> errorHandler(MethodArgumentNotValidException e)
    {
        log.error(null, e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Response.builder()
                .message(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
                .build());
    }
}
