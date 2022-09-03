package com.bridgelabz.lmsadmin.exception;

public class AdminException extends RuntimeException{
    private int statusCode;
    private String statusMessage;

    public AdminException(int statusCode, String statusMessage){
        super(statusMessage);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
