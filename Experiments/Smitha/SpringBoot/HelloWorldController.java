package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello (@RequestParam(value ="name") String name){
        //(@RequestParam(value ="name") String name)
        //return "Hello World!";
        return "Hello" + name + "!";
    }

}
