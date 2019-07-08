package br.edu.ifpe.missao06.DAO;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpe.missao06.model.Empresa;

@Repository
public interface EmpresaDAO extends JpaRepository<Empresa, Integer> {
	@Query("SELECT e FROM Empresa e WHERE e.id = :id")
	public Empresa getById(Integer id);
	
	//@Query
	public List<Empresa> findByNome(String nome);
	
	@Query("SELECT e FROM Empresa e ORDER BY e.nome")
	public List<Empresa> findByNomeDesc(Pageable pageable);

	@Query("SELECT e FROM Empresa e WHERE e.nome LIKE %:filtro% OR e.nomeCurto LIKE %:filtro% ORDER BY e.nome")
	public List<Empresa> filterByNomeNomeCurto(String filtro);

	@Transactional @Modifying
	@Query("UPDATE Empresa e SET e.nomeCurto = :nomeCurto, e.email = :email, e.isPrincipal = :isPrincipal, e.situacao = :situacao WHERE e.id = :id")
	public void updateById(Integer id, String nomeCurto, String email, Boolean isPrincipal, Boolean situacao);
}
