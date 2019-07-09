package br.edu.ifpe.missao06.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.ifpe.missao06.DAO.CargoDAO;
import br.edu.ifpe.missao06.exception.ServiceException;
import br.edu.ifpe.missao06.model.Cargo;

@Service
public class CargoService {

	@Autowired
	private CargoDAO cargoRep;

	/* ## FINDS ## */
	public Cargo findById(Integer id) {
		return this.cargoRep.getById(id);
	}

	public List<Cargo> findByNome(String nome) {
		return this.cargoRep.findByNome(nome);
	}

	public List<Cargo> findByNomeTop10() {
		return this.cargoRep.findByNomeDesc(PageRequest.of(0, 10));
	}

	public List<Cargo> findByNomeNomeCurto(String filtro) {
		return this.cargoRep.filterByNomeNomeCurto(filtro);
	}

	/* ## SAVE ## */
	public void save(Cargo cargo) throws ServiceException {
		if (!this.findByNome(cargo.getNome()).isEmpty()) {
			throw new ServiceException("Já existe empresa com o nome informado");
		}

		this.cargoRep.save(cargo);
	}

	public void update(Cargo cargo) {
		this.cargoRep.updateById(cargo.getId(), cargo.getNome(), cargo.getDescricao(), cargo.getDescricaoCurta(),
				cargo.getSituacao());
	}

	/* ## DELETE ## */
	public void deleteById(Integer id) {
		this.cargoRep.deleteById(id);
	}

	/* ## UTILS ## */
	public int naoListadosPorNomeTop10() {
		int count = (int) this.cargoRep.count() - 10;

		if (count < 0) {
			return 0;
		}

		return count;
	}
}
