package com.disney.exception;

public class ParamNotFound extends RuntimeException{
    public ParamNotFound(String error){
        super(error);
    }
}
