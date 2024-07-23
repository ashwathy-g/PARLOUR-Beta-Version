package com.example.ParlourApp.dto;

import lombok.Data;

@Data
public class ParlourStatusResponse {
    private String token;
    private String message;
    private Integer status;

    public ParlourStatusResponse(String token, String message,Integer status) {
        this.token = token;
        this.message = message;
        this.status = status;
    }
}
