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

import br.edu.ifpe.missao06.exception.ServiceException;
import br.edu.ifpe.missao06.model.Funcionario;
import br.edu.ifpe.missao06.service.CargoService;
import br.edu.ifpe.missao06.service.EmpresaService;
import br.edu.ifpe.missao06.service.FuncionarioService;

@Controller
@RequestMapping("/funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private CargoService cargoService;

	@Autowired
	private EmpresaService empresaService;

	/*
	 * SALVAR FUNCIONARIOS
	 */
	@GetMapping("/novo")
	public ModelAndView exibirForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("funcionario-form");

		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

		Funcionario funcionario = new Funcionario();
		if (inputFlashMap != null) {
			funcionario = (Funcionario) inputFlashMap.get("funcionario");
		}

		mv.addObject("funcionario", funcionario);
		mv.addObject("cargos", this.cargoService.findAllActive());
		mv.addObject("empresas", this.empresaService.findAllActive());

		return mv;
	}
	
	@PostMapping("/novo")
	public String salvarFuncionario(@Valid @ModelAttribute Funcionario funcionario, Errors errors, BindingResult br, RedirectAttributes ra) {
		String redirect;
		Integer id = null;
		if (funcionario.getId() != null) {
			id = funcionario.getId();
			redirect = "redirect:/funcionario/editar/" + id;
			ra.addFlashAttribute("alertSucesso", "Funcionário [" + funcionario.getNome() + "] editado com sucesso!");
		} else {
			redirect = "redirect:/funcionario/novo";
			ra.addFlashAttribute("alertSucesso", "Funcionário [" + funcionario.getNome() + "] criado com sucesso!");
		}
		
		if (errors.hasErrors()) {
			ra.addFlashAttribute("errors", errors.getFieldErrors());
			ra.addFlashAttribute("funcionario", funcionario);
		} else {
			try {
				if (id != null) {
					Funcionario other = this.funcionarioService.findById(id);
					if (funcionario.getNome().equals(other)) {
						this.funcionarioService.update(funcionario);
					} else {
						this.funcionarioService.save(funcionario);
					}
				} else {
					this.funcionarioService.save(funcionario);
				}
				
				return "redirect:/funcionario/listar";
			} catch (ServiceException e) {
				ra.addFlashAttribute("mesagemErro", "Não foi possível salvar o funcinário: " + e.getMessage());
				ra.addFlashAttribute("funcionario", funcionario);
				ra.addFlashAttribute("cargos", this.cargoService.findAllActive());
				ra.addFlashAttribute("empresas", this.empresaService.findAllActive());
			}
		}
		
		return redirect;
	}
}
