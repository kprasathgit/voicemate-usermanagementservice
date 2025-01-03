package com.voicemate.usermanagementservice;

import java.security.Security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.voicemate.usermanagementservice.interceptor.HttpInterceptor;

@SpringBootApplication
public class UserManagementServiceApplication implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HttpInterceptor());

	}

	public static void main(String[] args) {

		String disabledAlgorithms = Security.getProperty("jdk.tls.disabledAlgorithms");
		String[] algorithms = disabledAlgorithms.split(",");
		for (int i = 0; i < algorithms.length; i++) {
			String string = algorithms[i];
			if ("TLSv1".contains(string.trim()) || "TLSv1.1".contains(string.trim())) {
				algorithms[i] = "";
			}
		}
		String altered = "";

		for (int i = 0; i < algorithms.length; i++) {

			String string = algorithms[i];
			if (null == string) {
				continue;
			}
			altered += string + ",";
		}

		Security.setProperty("jdk.tls.disabledAlgorithms", altered);
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}

}
