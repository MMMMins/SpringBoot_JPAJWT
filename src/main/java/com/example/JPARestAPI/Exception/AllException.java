package com.example.JPARestAPI.Exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AllException extends RuntimeException{
    private final ErrorCode errorCode;

}
