package com.example.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    @Autowired
    private MessageSource messageSource;

    //GET
    // /hello-world (endpoint)
    //@RequestMapping(mehtod=RequestMethod.Get, path="/hello-world")
    @GetMapping(path ="/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    //alt + enter
    @GetMapping(path ="/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path ="/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable(value="name") String name){
        return new HelloWorldBean(String.format("Hello World, %s",name));
    }

    @GetMapping(path="/hello-world-internationalized") // 헤더에 locale설정해서 던짐 Accept-Language : fr
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language",required = false) Locale locale){
        //null : 키값(문자열 파라미터)
        return messageSource.getMessage("greeting.message", null, locale);
    }


}
