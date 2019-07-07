package br.edu.ifpe.missao06.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import br.edu.ifpe.missao06.model.Empresa;
import br.edu.ifpe.missao06.service.EmpresaService;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;

	/*
	 * LISTAR EMPRESAS
	*/
	@GetMapping("/listar")
	public ModelAndView listarEmpresas() {
		ModelAndView mv = new ModelAndView("/listar-empresas");
		
		return mv;
	}

	/*
	 * ADICIONAR EMPRESA
	*/

	@GetMapping("/nova")
	public ModelAndView exibirForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/empresa-form");
		
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			Empresa empresa = (Empresa) inputFlashMap.get("empresa");
			mv.addObject(empresa);
		} else {
			mv.addObject("empresa", new Empresa());
		}

		return mv;
	}

	@PostMapping("/nova")
	public String adicionarEmpresa(@Valid @ModelAttribute Empresa empresa, Errors errors, BindingResult br, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			ra.addFlashAttribute("errors", errors.getFieldErrors());
			ra.addFlashAttribute("empresa", empresa);
		} else {
			try {
				this.empresaService.novaEmpresa(empresa);
				ra.addFlashAttribute("mensagem", "Empresa [" + empresa.getNome() + "[ criada com sucesso!");

				return "redirect:/empresa/listar";
			} catch (Exception e) {
				ra.addFlashAttribute("mensagemErro", "Não foi possível salvar a empresa: " + e.getMessage());
			}
		}

		return "redirect:/empresa/nova";
	}
}
