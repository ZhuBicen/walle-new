package com.walle.gitBookHost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@SpringBootApplication
public class GitBookHostApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitBookHostApplication.class, args);
	}

	@GetMapping("/")
	String hello(@AuthenticationPrincipal OAuth2User oauth2User ) {
		if (oauth2User == null) {
			return "";
		}
		System.out.println("userName: " + oauth2User.getName());
		for(Map.Entry<String, Object> entry: oauth2User.getAttributes().entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		return oauth2User.getName();
	}

}
