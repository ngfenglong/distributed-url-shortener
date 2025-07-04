package com.zell.dev.shorten_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.zell.dev.shorten_api",
		"com.zell.dev.common_lib",
		"com.zell.dev.common_service"
})
public class ShortenApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShortenApiApplication.class, args);
	}

}
