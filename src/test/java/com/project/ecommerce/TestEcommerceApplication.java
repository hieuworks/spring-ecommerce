package com.project.ecommerce;

import org.springframework.boot.SpringApplication;

public class TestEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.from(com.project.ecommerce.EcommerceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
