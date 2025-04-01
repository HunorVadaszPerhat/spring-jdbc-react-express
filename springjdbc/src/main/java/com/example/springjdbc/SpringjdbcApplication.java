package com.example.springjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@EnableCaching
@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL) // 👈 important!
public class SpringjdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringjdbcApplication.class, args);
	}

}
