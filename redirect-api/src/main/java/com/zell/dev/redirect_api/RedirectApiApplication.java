package com.zell.dev.redirect_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.zell.dev.redirect_api",
		"com.zell.dev.common_lib",
		"com.zell.dev.common_service"
})
public class RedirectApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedirectApiApplication.class, args);
	}
}
