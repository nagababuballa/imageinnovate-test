package com.example.demo.exception;

public class InvaildDateFormatException extends  RuntimeException{

    public InvaildDateFormatException(String exceptionMsg){
        super(exceptionMsg);
    }
}
