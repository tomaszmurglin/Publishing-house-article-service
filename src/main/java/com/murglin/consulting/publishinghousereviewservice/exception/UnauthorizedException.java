package com.murglin.consulting.publishinghousereviewservice.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message){
        super(message);
    }
}
