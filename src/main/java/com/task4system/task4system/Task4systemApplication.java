package com.task4system.task4system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Task4systemApplication {

	public static void main(String[] args) {

		try {
			JsonDataHandler.generateSampleData("users.json", 10);
		} catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		SpringApplication.run(Task4systemApplication.class, args);
	}

}
