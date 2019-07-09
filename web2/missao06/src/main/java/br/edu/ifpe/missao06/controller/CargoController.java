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

import br.edu.ifpe.missao06.model.Cargo;
import br.edu.ifpe.missao06.service.CargoService;

@Controller
@RequestMapping("/cargo")
public class CargoController {

	@Autowired
	private CargoService cargoService;

	/*
	 * LISTAR CARGOS
	 */
	@GetMapping({ "", "/", "/listar" })
	public ModelAndView listarCargos() {
		ModelAndView mv = new ModelAndView("cargo-list");
		mv.addObject("cargos", this.cargoService.findByNomeTop10());
		mv.addObject("naoListados", this.cargoService.naoListadosPorNomeTop10());

		return mv;
	}

	@GetMapping("/filtrar")
	public ModelAndView exibirResultado(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/cargo-list");

		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			mv.addObject("cargos", inputFlashMap.get("cargos"));
			mv.addObject("resultado", inputFlashMap.get("resultado"));
		} else {
			mv.setViewName("redirect:/cargo/listar");
		}

		return mv;
	}
	
	@PostMapping("/filtrar")
	public String filtrarEmpresas(@RequestParam(name = "pesquisa") String pesquisa, RedirectAttributes ra) {
		List<Cargo> cargos = this.cargoService.findByNomeNomeCurto(pesquisa);
		if (cargos.size() == 1) {
			return "redirect:/cargo/editar/" + cargos.get(0).getId();
		}
		
		ra.addFlashAttribute("cargos", cargos);
		ra.addFlashAttribute("resultado", "Exibindo resultados para pesquisa: " + pesquisa);
		
		return "redirect:/cargo/filtrar";
	}

	/*
	 * ADICIONAR CARGO
	 */
	@GetMapping("/novo")
	public ModelAndView exibirForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/cargo-form");

		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			Cargo cargo = (Cargo) inputFlashMap.get("cargo");
			mv.addObject("cargo", cargo);
		} else {
			mv.addObject("cargo", new Cargo());
		}

		return mv;
	}

	@PostMapping({ "/novo", "/salvar" })
	public String savarCargo(@Valid @ModelAttribute Cargo cargo, Errors errors, BindingResult br,
			RedirectAttributes ra) {
		String redirect;
		Integer id = null;
		if (cargo.getId() != null) {
			id = cargo.getId();
			redirect = "redirect:/cargo/editar/" + cargo.getId();
			ra.addFlashAttribute("alertSucesso", "Cargo [" + cargo.getNome() + "] editado com sucesso!");
		} else {
			redirect = "redirect:/cargo/novo";
			ra.addFlashAttribute("alertSucesso", "Cargo [" + cargo.getNome() + "] criado com sucesso!");
		}

		if (errors.hasErrors()) {
			ra.addFlashAttribute("errors", errors.getFieldErrors());
			ra.addFlashAttribute("cargo", cargo);
		} else {
			try {
				if (id != null) {
					Cargo other = this.cargoService.findById(id);
					if (cargo.getNome().equals(other.getNome())) {
						this.cargoService.update(cargo);
					} else {
						this.cargoService.save(cargo);
					}
				} else {
					this.cargoService.save(cargo);
				}

				return "redirect:/cargo/listar";
			} catch (Exception e) {
				ra.addAttribute("mesagemErro", "Não foi possivel salvar o cargo: " + e.getMessage());
				ra.addFlashAttribute("cargo", cargo);
			}

		}

		return redirect;
	}

	/*
	 * EDITAR CARGO
	 */
	@GetMapping("/editar/{codigo}")
	public ModelAndView editarCargo(@PathVariable String codigo, RedirectAttributes ra) {
		ModelAndView mv = new ModelAndView("redirect:/cargo/listar");

		Integer id = null;
		try {
			id = Integer.parseInt(codigo);
		} catch (NumberFormatException e) {
			ra.addFlashAttribute("alertErro", "Você tentou editar um Cargo invalido");

			return mv;
		}

		Cargo cargo = this.cargoService.findById(id);
		if (cargo != null) {
			mv.setViewName("/cargo-form");
			mv.addObject("cargo", cargo);
			mv.addObject("editar", true);
		} else {
			ra.addFlashAttribute("alertErro", "Você tentou editar um Cargo invalido");
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
			ra.addFlashAttribute("alertErro", "Você tentou editar um Cargo invalido");

			return "redirect:/cargo/listar";
		}

		Cargo cargo = this.cargoService.findById(id);
		if (cargo != null) {
			this.cargoService.deleteById(id);
			ra.addFlashAttribute("alertSucesso", "Cargo [" + cargo.getNome() + "] deletado com sucesso!");
		} else {
			ra.addFlashAttribute("alertErro", "Você tentou editar um Cargo invalido");
		}

		return "redirect:/cargo/listar";
	}

	@PostMapping("/excluir/varios")
	public String deletarVarios(@RequestParam(value = "selecionados", required = true) String[] selecionados,
			RedirectAttributes ra) {
		StringBuilder alertMessage = new StringBuilder();
		for (String s : selecionados) {
			Integer id = null;
			try {
				id = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				alertMessage.append("Você tentou excluir um Cargo invalido");
			}

			if (id != null) {
				Cargo cargo = this.cargoService.findById(id);
				if (cargo != null) {
					this.cargoService.deleteById(id);
					alertMessage.append("Cargo [" + cargo.getNome() + "] excluido com sucesso!");
				} else {
					alertMessage.append("Você tentou excluir um Cargo invalido");
				}
			}
		}
		ra.addFlashAttribute("alertSucesso", alertMessage);

		return "redirect:/cargo/listar";
	}
}
