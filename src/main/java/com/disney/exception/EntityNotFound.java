package com.disney.exception;

public class EntityNotFound extends RuntimeException{
    public EntityNotFound(String error){
        super(error);
    }
}
