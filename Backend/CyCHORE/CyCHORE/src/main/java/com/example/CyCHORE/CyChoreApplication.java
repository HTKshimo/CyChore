package com.example.CyCHORE;

import com.example.CyCHORE.Task.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.example.CyCHORE.MyDatabase;


@SpringBootApplication
public class CyChoreApplication {

//	@Autowired
//	DataSource dataSource;
//	@Autowired
//	MyDatabase db;
//	@Autowired
//    TaskRepository tr;

//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(CyChoreApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(CyChoreApplication.class, args);
	}

//	public static void main(String[] args) {
//		SpringApplication.run(CyChoreApplication.class, args);
//	}

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
