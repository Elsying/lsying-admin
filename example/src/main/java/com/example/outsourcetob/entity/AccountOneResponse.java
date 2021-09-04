package com.example.outsourcetob.entity;

import lombok.Data;

import java.util.List;

@Data
public class AccountOneResponse {
    private String account;

    private String password;

    private List<String> device;

    private String server;

    private String hero;

    private String equipment;

    private Integer state;
}
