package br.edu.ifpe.missao06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpe.missao06.model.Empresa;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	
	@GetMapping("/nova")
	public ModelAndView exibirForm() {
		ModelAndView mv = new ModelAndView("/empresa-form");
		mv.addObject("empresa", new Empresa());
		
		return mv;
	}
}
