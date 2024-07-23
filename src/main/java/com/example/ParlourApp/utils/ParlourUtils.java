package com.example.ParlourApp.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ParlourUtils
{
    private ParlourUtils(){}
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}",httpStatus);
    }
}


