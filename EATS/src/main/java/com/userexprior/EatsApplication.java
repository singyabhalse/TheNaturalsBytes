package com.userexprior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.userexprior.config.ClassPathScannerConfig;

@SpringBootApplication
@ComponentScan(basePackages = "com.userexprior")
@EnableScheduling
@Import(ClassPathScannerConfig.class)
public class EatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatsApplication.class, args);
		System.out.println("EatsApplication----Welcome");
	}

}
