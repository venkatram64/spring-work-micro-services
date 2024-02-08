package com.venkat.postconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer  //to register with Eureka server
public class PostConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostConfigServerApplication.class, args);
	}

}
