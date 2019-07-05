package br.edu.ifpe.missao06.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpe.missao06.model.Empresa;
import br.edu.ifpe.missao06.service.EmpresaService;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping("/nova")
	public ModelAndView exibirForm() {
		ModelAndView mv = new ModelAndView("/empresa-form");
		mv.addObject("empresa", new Empresa());
		
		return mv;
	}
	
	@PostMapping("/nova")
	public String adicionarEmpresa(@Valid @ModelAttribute Empresa empresa, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			ra.addFlashAttribute("mensagemErro", "Não foi possível adicionar a empresa");
		} else {
			try {
				this.empresaService.novaEmpresa(empresa);
				ra.addFlashAttribute("mensagem", "Empresa [" + empresa.getNome() + "[ criada com sucesso!");
			} catch (Exception e) {
				ra.addFlashAttribute("MensagemErro", "Não foi possível salvar a empresa: " + e.getMessage());
			}
		}
		
		return "redirect:/empresa/nova";
	}
}
