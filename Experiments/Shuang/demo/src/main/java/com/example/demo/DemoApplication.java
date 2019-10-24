package com.example.demo;

import com.example.demo.owners.*;
import org.reflections.Reflections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.annotation.Annotation;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("database is running...");
    }

}
