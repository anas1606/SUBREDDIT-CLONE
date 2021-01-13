package com.example.redditclone.Exception;

public class NoPosts extends RuntimeException{
    public NoPosts(String message){
        super(message);
    }
}
