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

	@GetMapping("/getUserName")
	String hello(@AuthenticationPrincipal OAuth2User oauth2User ) {
		return getLoginName(oauth2User);
	}

	@GetMapping("/getUserInfo")
	OAuth2User getOauth2User(@AuthenticationPrincipal OAuth2User oauth2User ) {
		return oauth2User;
	}
	private String getLoginName(OAuth2User oauth2User) {
		if (oauth2User == null) {
			return "";
		}
		return oauth2User.getAttributes().get("login").toString();
	}

}
