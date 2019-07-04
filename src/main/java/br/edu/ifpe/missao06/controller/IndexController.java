package br.edu.ifpe.missao06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping({"/", "/index"})
	public String exibirIndex() {
		return "/index";
	}
}
