package com.userexprior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.userexprior")
@EnableScheduling
public class EatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatsApplication.class, args);
		System.out.println("EatsApplication----Welcome");
	}

}
