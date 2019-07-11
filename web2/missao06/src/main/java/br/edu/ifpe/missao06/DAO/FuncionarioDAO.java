package br.edu.ifpe.missao06.DAO;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifpe.missao06.model.Cargo;
import br.edu.ifpe.missao06.model.Empresa;
import br.edu.ifpe.missao06.model.Funcionario;

@Repository
public interface FuncionarioDAO extends JpaRepository<Funcionario, Integer> {

	@Query("FROM Funcionario f WHERE f.cpf = :cpf")
	public Funcionario findByCpf(String cpf);
	
	public List<Funcionario> findByNome(String nome);
	
	public boolean existsByCargo(Example<Cargo> exemplo);
	
	public boolean existsByEmpresa(Example<Empresa> exemplo);
}
