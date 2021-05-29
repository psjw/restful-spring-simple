package com.example.restfulwebservice.helloworld;
// lombok

import lombok.AllArgsConstructor;
import lombok.Data;

@Data //setter/getter/toString
@AllArgsConstructor
public class HelloWorldBean {
    private String message;
}
