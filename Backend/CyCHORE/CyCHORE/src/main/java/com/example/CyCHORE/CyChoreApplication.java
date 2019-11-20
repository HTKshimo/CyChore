package com.example.CyCHORE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CyChoreApplication{

//	@Autowired
//	DataSource dataSource;
//	@Autowired
//	@Autowired
//    TaskRepository tr;

	public static void main(String[] args) {
		SpringApplication.run(CyChoreApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("Our DataSource is = " + dataSource);
//		Iterable<com.example.CyCHORE.Task.Task> p = tr.findAll();
//		System.out.println(p.iterator().hasNext());
//		for (com.example.CyCHORE.Task.Task p1 : p) {
//			System.out.println("Here is a system: " + p1.toString());
//		}
//	}

}
