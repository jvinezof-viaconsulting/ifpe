package br.edu.ifpe.missao06.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpe.missao06.DAO.EmpresaDAO;
import br.edu.ifpe.missao06.exception.ServiceException;
import br.edu.ifpe.missao06.model.Empresa;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaDAO empresaRep;

	public List<Empresa> findByNome(String nome) {
		return this.empresaRep.findByNome(nome);
	}

	public void novaEmpresa(Empresa empresa) throws ServiceException {
		if (this.findByNome(empresa.getNome()) != null) {
			throw new ServiceException("Já existe empresa com o nome informado");
		}
		
		this.empresaRep.save(empresa);
	}
}
