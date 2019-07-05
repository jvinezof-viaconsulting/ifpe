package br.edu.ifpe.missao06.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpe.missao06.model.Empresa;

@Repository
public interface EmpresaDAO extends JpaRepository<Empresa, Integer> {
	public List<Empresa> findByNome(String nome);
}
