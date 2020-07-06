package com.gmail.krzgrz.demo.currencydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.gmail.krzgrz.demo.service"})
public class CurrencyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyDemoApplication.class, args);
	}

}
