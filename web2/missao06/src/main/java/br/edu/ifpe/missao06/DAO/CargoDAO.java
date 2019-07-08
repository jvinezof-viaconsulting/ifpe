package br.edu.ifpe.missao06.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpe.missao06.model.Cargo;

@Repository
public interface CargoDAO extends JpaRepository<Cargo, Integer> {

}
