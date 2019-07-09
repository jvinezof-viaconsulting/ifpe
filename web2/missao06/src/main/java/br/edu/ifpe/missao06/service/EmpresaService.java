package br.edu.ifpe.missao06.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ifpe.missao06.DAO.EmpresaDAO;
import br.edu.ifpe.missao06.exception.ServiceException;
import br.edu.ifpe.missao06.model.Empresa;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaDAO empresaRep;

	/* ## FINDS ## */
	public Empresa findById(Integer id) {
		return this.empresaRep.getById(id);
	}

	public List<Empresa> findByNome(String nome) {
		return this.empresaRep.findByNome(nome);
	}

	public List<Empresa> findByNomeTop10() {
		return this.empresaRep.findByNomeDesc(PageRequest.of(0, 10));
	}
	
	public List<Empresa> filterByNomeNomeCurto(String filtro) {
		return this.empresaRep.filterByNomeNomeCurto(filtro);
	}

	/* ## SAVE ## */
	public void save(Empresa empresa) throws ServiceException {
		if (!this.findByNome(empresa.getNome()).isEmpty()) {
			throw new ServiceException("Já existe empresa com o nome informado");
		}
		
		this.empresaRep.save(empresa);
	}
	
	public void update(Empresa empresa) {
		this.empresaRep.updateById(empresa.getId(), empresa.getNomeCurto(), empresa.getEmail(), empresa.getIsPrincipal(), empresa.getSituacao());
	}

	/* ## DELETE ## */
	public void deleteById(Integer id) {
		this.empresaRep.deleteById(id);
	}
	
	/* ## UTILS ## */
	public int naoListadosPorNomeTop10() {
		int count = (int) this.empresaRep.count() - 10;

		if (count < 0) {
			return 0;
		}

		return count;
	}
}
