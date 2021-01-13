package com.example.redditclone.exception;

public class NoPosts extends RuntimeException{
    public NoPosts(String message){
        super(message);
    }
}
