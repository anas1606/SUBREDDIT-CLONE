package com.example.redditclone.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class ExceptionDetail {
    private Date timestamp;
    private String message;
    private String detail;

    public ExceptionDetail(Date timestamp, String message, String detail) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
    }
}
