package br.com.joelamalio.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrosController {

	@GetMapping("/404")
	public String pageNotFound() {
		return "404";
	}
	
	@RequestMapping("/500")
	public String internalServerError() {
		return "500";
	}
	
}
