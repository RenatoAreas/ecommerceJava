package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class EcommerceClientes1Application {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceClientes1Application.class, args);
	}

}
