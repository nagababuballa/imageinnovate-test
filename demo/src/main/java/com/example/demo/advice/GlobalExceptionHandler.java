package com.example.demo.advice;

import com.example.demo.exception.InvaildDateFormatException;
import com.example.demo.exception.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvaildDateFormatException.class)
    public ResponseEntity<ErrorData> handleInvalidDateFormatException(InvaildDateFormatException e){
        ErrorData errorData = prepareErrorData(HttpStatus.BAD_REQUEST,e);
        return new ResponseEntity<>(errorData,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorData> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,Object> data = new HashMap<>();
        for(FieldError fieldError: e.getBindingResult().getFieldErrors()) {
            data.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        ErrorData errorData = prepareErrorData(HttpStatus.BAD_REQUEST,e);
        errorData.setErrors(data);
        return new ResponseEntity<>(errorData,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorData> handleAllExceptions(Exception e){
        ErrorData errorData = prepareErrorData(HttpStatus.INTERNAL_SERVER_ERROR,e);
        return new ResponseEntity<>(errorData,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorData> handleJwtException(JwtException e){
        ErrorData errorData = prepareErrorData(HttpStatus.INTERNAL_SERVER_ERROR,e);
        return new ResponseEntity<>(errorData,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ErrorData prepareErrorData(HttpStatus httpStatus,Exception e){
        return ErrorData.builder()
                .statusCode(httpStatus.value())
                .reason(httpStatus.getReasonPhrase())
                .date(LocalDate.now())
                .message(e.getMessage())
                .build();
    }
}
