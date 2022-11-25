package com.udemy.blogproject.springbootblogrestapi.exception;

import com.udemy.blogproject.springbootblogrestapi.payload.ErrorDetails;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handle validation exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String message= error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



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
