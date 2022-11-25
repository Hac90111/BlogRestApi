package com.udemy.blogproject.springbootblogrestapi.exception;

import com.udemy.blogproject.springbootblogrestapi.payload.ErrorDetails;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler{

    //handle specific exceptions and
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request){
        ErrorDetails details= new ErrorDetails(new Date(), e.getMessage(),request.getDescription(false));
        return  new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException e, WebRequest request){
        ErrorDetails details= new ErrorDetails(new Date(), e.getMessage(),request.getDescription(false));
        return  new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

//    global exception
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception e, WebRequest request){
     ErrorDetails details= new ErrorDetails(new Date(), e.getMessage(),request.getDescription(false));
        return  new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
