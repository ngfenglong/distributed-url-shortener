package com.zell.dev.redirect_api.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceName, String key){
        super(String.format("%s not found with the given key - %s",resourceName, key));
    }
}
