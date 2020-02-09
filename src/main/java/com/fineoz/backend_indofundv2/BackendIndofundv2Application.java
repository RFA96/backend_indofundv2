package com.fineoz.backend_indofundv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendIndofundv2Application {

	public static void main(String[] args) {
		SpringApplication.run(BackendIndofundv2Application.class, args);
	}

}
