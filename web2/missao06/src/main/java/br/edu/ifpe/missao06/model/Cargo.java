package br.edu.ifpe.missao06.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Cargo {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cargo_id")
	private Integer id;

	@NotBlank(message = "Descrição do cargo não pode ser vazia")
	@Size(max = 70, message = "Descrição do cargo não pode ter mais de 70 caracteres")
	@Column(name = "cargo_descricao", length = 70, nullable = false)
	private String descricao;

	@Column(name = "cargo_descricao_curta")
	private String descricaoCurta;

	@Column(name = "cargo_situacao")
	private Boolean situacao = true;
}
