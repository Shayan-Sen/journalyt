package com.shayan.journalyt.controller;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse extends RepresentationModel<ApiResponse> {
    private Object data;
    private String message;
}
