package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class DemoApplication extends HelloWorldController{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("HELLO WORLD!");
		//System.out.println(HelloWorldController.class);
		HelloWorldController test = new HelloWorldController();
		test.sayHello("Smitha");
	}
}
