package com.voicemate.translationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = { "com.voicemate.translationservice", "com.voicemate.translationservicecommon" })
public class TranslationserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslationserviceApplication.class, args);
	}

}
