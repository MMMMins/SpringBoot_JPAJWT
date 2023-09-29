package com.example.JPARestAPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultMessageDTO {
    private String msg;
    private int statusCode;
}
