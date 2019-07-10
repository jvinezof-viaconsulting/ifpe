package br.edu.ifpe.missao06.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.edu.ifpe.missao06.DAO.FuncionarioDAO;
import br.edu.ifpe.missao06.exception.ServiceException;
import br.edu.ifpe.missao06.model.Funcionario;
import br.edu.ifpe.missao06.util.Cpf;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioDAO funcionarioRep;

	public List<Funcionario> findAll() {
		return this.funcionarioRep.findAll(Sort.by("nome"));
	}

	public Funcionario findById(Integer id) {
		return this.findById(id);
	}

	/* ## SAVE ## */
	public void save(Funcionario funcionario) throws ServiceException {
		if (!Cpf.verificarDigito(funcionario.getCpf())) {
			throw new ServiceException("Dígito verificador do CPF não confere");
		}

		Funcionario other = this.funcionarioRep.findByCpf(funcionario.getCpf());
		if (other != null) {
			throw new ServiceException("CPF já informado para funcionário" + other.getId());
		}
		
		if (Cpf.numerosRepitidos(funcionario.getCpf())) {
			throw new ServiceException("Número do CPF inválido.");
		}
		
		LocalDate hoje = LocalDate.now();
		Period period = Period.between(funcionario.getDataNascimento(), hoje);
		if (period.getYears() < 18) {
			throw new ServiceException("O funcionário terá que possuir, no mínimo, 18 anos de idade");
		}
		
		this.funcionarioRep.save(funcionario);
	}

	public void update(Funcionario funcionario) throws ServiceException {
		if (!Cpf.verificarDigito(funcionario.getCpf())) {
			throw new ServiceException("Dígito verificador do CPF não confere");
		}
		
		if (Cpf.numerosRepitidos(funcionario.getCpf())) {
			throw new ServiceException("Número do CPF inválido.");
		}
		
		LocalDate hoje = LocalDate.now();
		Period period = Period.between(funcionario.getDataNascimento(), hoje);
		if (period.getYears() < 18) {
			throw new ServiceException("O funcionário terá que possuir, no mínimo, 18 anos de idade");
		}
		
		this.funcionarioRep.save(funcionario);
	}
}
