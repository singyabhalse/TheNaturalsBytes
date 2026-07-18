package com.userexprior;

import com.userexprior.config.AccessPolicyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.userexprior")
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.userexprior.repository")
public class EatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatsApplication.class, args);
		System.out.println("EatsApplication----Welcome");
	}

}
