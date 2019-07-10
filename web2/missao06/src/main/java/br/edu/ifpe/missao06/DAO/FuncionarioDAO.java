package br.edu.ifpe.missao06.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifpe.missao06.model.Funcionario;

@Repository
public interface FuncionarioDAO extends JpaRepository<Funcionario, Integer> {
	
	@Query("FROM Funcionario f WHERE f.cpf = :cpf")
	public Funcionario findByCpf(String cpf);
}
