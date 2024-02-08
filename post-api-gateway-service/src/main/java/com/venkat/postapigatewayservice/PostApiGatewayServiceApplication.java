package com.venkat.postapigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //to register with Eureka server
public class PostApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostApiGatewayServiceApplication.class, args);
	}

}
