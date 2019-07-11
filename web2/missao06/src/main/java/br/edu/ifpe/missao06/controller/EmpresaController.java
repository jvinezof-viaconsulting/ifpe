package br.edu.ifpe.missao06.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import br.edu.ifpe.missao06.model.Empresa;
import br.edu.ifpe.missao06.service.EmpresaService;
import br.edu.ifpe.missao06.service.FuncionarioService;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private FuncionarioService funcionarioService;

	/*
	 * LISTAR EMPRESAS
	*/
	@GetMapping({"", "/", "/listar"})
	public ModelAndView listarEmpresas() {
		ModelAndView mv = new ModelAndView("/empresa-list");
		mv.addObject("empresas", this.empresaService.findByNomeTop10());
		mv.addObject("naoListados", this.empresaService.naoListadosPorNomeTop10());

		return mv;
	}

	@GetMapping("/filtrar")
	public ModelAndView exibirResultado(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/empresa-list");

		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			mv.addObject("empresas", inputFlashMap.get("empresas"));
			mv.addObject("resultado", inputFlashMap.get("resultado"));
		} else {
			mv.setViewName("redirect:/empresa/listar");
		}

		return mv;
	}
	
	@PostMapping("/filtrar")
	public String filtrarEmpresas(@RequestParam(name = "pesquisa") String pesquisa, RedirectAttributes ra) {
		List<Empresa> empresas = this.empresaService.filterByNomeNomeCurto(pesquisa);
		if (empresas.size() == 1) {
			return "redirect:/empresa/editar/" + empresas.get(0).getId();
		}

		ra.addFlashAttribute("empresas", empresas);
		ra.addFlashAttribute("resultado", "Exibindo resultados para pesquisa: " + pesquisa);
		
		return "redirect:/empresa/filtrar";
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

	@PostMapping({"/nova", "/salvar"})
	public String salvarEmpresa(@Valid @ModelAttribute Empresa empresa, Errors errors, BindingResult br, RedirectAttributes ra) {
		String redirect;

		Integer id = null;
		if (empresa.getId() != null) {
			id = empresa.getId();
			redirect = "redirect:/empresa/editar/" + empresa.getId();
			ra.addFlashAttribute("alertSucesso", "Empresa [" + empresa.getNome() + "] editada com sucesso!");
		} else {
			redirect = "redirect:/empresa/nova";
			ra.addFlashAttribute("alertSucesso", "Empresa [" + empresa.getNome() + "] criada com sucesso!");
		}
		
		if (errors.hasErrors()) {
			ra.addFlashAttribute("errors", errors.getFieldErrors());
			ra.addFlashAttribute("empresa", empresa);
		} else {
			try {
				if (id != null) {
					Empresa other = this.empresaService.findById(id);
					if (empresa.getNome().equals(other.getNome())) {
						this.empresaService.update(empresa);
					} else {
						this.empresaService.save(empresa);
					}
				} else {
					this.empresaService.save(empresa);
				}

				return "redirect:/empresa/listar";
			} catch (Exception e) {
				ra.addFlashAttribute("mensagemErro", "Não foi possível salvar a empresa: " + e.getMessage());
				ra.addFlashAttribute("empresa", empresa);
			}
		}

		return redirect;
	}
	
	/*
	 * EDITAR EMPRESA
	*/
	@GetMapping("/editar/{codigo}")
	public ModelAndView editarEmpresa(@PathVariable String codigo, RedirectAttributes ra) {
		ModelAndView mv = new ModelAndView("redirect:/empresa/listar");
		
		Integer id = null;
		try {
			 id = Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			ra.addFlashAttribute("alertErro", "Você tentou editar uma Empresa invalida");

			return mv;
		}

		Empresa empresa = this.empresaService.findById(id);
		if (empresa != null) {
			mv.setViewName("/empresa-form");
			mv.addObject("empresa", empresa);
			mv.addObject("editar", true);
		} else {
			ra.addFlashAttribute("alertErro", "Você tentou editar uma Empresa invalida");
		}

		return mv;
	}
	
	/*
	 * DELETAR EMPRESA
	*/
	@GetMapping("/excluir/{codigo}")
	public String deletarUm(@PathVariable String codigo, RedirectAttributes ra) {

		Integer id = null;
		try {
			 id = Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			ra.addFlashAttribute("alertErro", "Você tentou excluir uma Empresa invalida");
			
			return "redirect:/empresa/listar";
		}

		Empresa empresa = this.empresaService.findById(id);
		if (empresa != null) {
			if (!this.funcionarioService.existsByEmpresa(empresa)) {
				this.empresaService.deleteById(id);
				ra.addFlashAttribute("alertSucesso", "Empresa [" + empresa.getNome() + "] excluida com sucesso");
			}
		} else {
			ra.addFlashAttribute("alertErro", "Você tentou excluir uma Empresa invalida");
		}
		
		return "redirect:/empresa/listar";
	}
	
	@PostMapping("/excluir/varios")
	public String deletarVarios(@RequestParam(value = "selecionados", required = true) String[] selecionados, RedirectAttributes ra) {
		StringBuilder alertMessage = new StringBuilder();
		for(String s : selecionados) {
			Integer id = null;
			try {
				 id = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				alertMessage.append("Você tentou excluir uma Empresa invalida.");
			}

			if (id != null) {
				Empresa empresa = this.empresaService.findById(id);
				if (empresa != null) {
					this.empresaService.deleteById(id);
					alertMessage.append("Empresa [" + empresa.getNome() + "] excluida com sucesso. ");
				} else {
					alertMessage.append("Você tentou excluir uma Empresa invalida.");
				}
			}
		}
		ra.addFlashAttribute("alertSucesso", alertMessage);

		return "redirect:/empresa/listar";
	}
}
