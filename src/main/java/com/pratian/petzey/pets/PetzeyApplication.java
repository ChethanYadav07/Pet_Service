package com.pratian.petzey.pets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

//@EnableEurekaClient
@SpringBootApplication
//@OpenAPIDefinition(info = @Info(title = "Petzey", version = "1.0", description = "Swagger for Petzey Pet Service"), servers = {
//		@Server(url = "https://petservice.bt.skillassure.com", description = "Default Server URL") })
public class PetzeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetzeyApplication.class, args);

	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}