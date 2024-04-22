package com.redis.demo.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String e){
        super(e);
    }
}
