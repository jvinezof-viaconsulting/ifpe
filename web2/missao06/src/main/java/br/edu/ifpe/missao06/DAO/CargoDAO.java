package br.edu.ifpe.missao06.DAO;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpe.missao06.model.Cargo;

@Repository
public interface CargoDAO extends JpaRepository<Cargo, Integer> {
	@Query("SELECT c FROM Cargo c WHERE c.id = :id")
	public Cargo getById(Integer id);
	
	//@Query
	public List<Cargo> findByNome(String nome);
	
	@Query("SELECT c FROM Cargo c ORDER BY c.nome")
	public List<Cargo> findByNomeDesc(Pageable pageable);
	
	@Query("SELECT c FROM Cargo c WHERE c.nome LIKE %:filtro% ORDER BY c.nome")
	public List<Cargo> filterByNomeNomeCurto(String filtro);
	
	@Transactional @Modifying
	@Query("UPDATE Cargo c SET c.nome = :nome, c.descricao = :descricao, c.descricaoCurta = :descricaoCurta, c.situacao = :situacao WHERE c.id = :id")
	public void updateById(Integer id, String nome, String descricao, String descricaoCurta, Boolean situacao);
}
