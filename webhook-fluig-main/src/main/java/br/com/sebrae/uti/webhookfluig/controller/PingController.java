package br.com.sebrae.uti.webhookfluig.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PingController {
	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
}
