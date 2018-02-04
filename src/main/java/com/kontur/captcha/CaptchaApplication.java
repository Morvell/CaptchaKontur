package com.kontur.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class CaptchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptchaApplication.class, args);
	}
}
