package br.edu.ifpe.missao06.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empresa_id")
	private Integer id;

	@Size(max = 50, message = "Informe o nome da empresa")
	@Column(name = "empresa_nome", length = 50, nullable = false)
	private String nome;

	@Size(max = 10)
	@Column(name = "empresa_nome_curto", length = 10)
	private String nomeCurto;

	@Size(max = 80)
	@Column(name = "empresa_email", length = 80)
	private String email;

	@NotNull(message = "Informe se é empresa princial")
	@Column(name = "empresa_is_principal", nullable = false)
	private Boolean isPrincipal;

	@Column(name = "empresa_situacao", nullable = false)
	private Boolean situacao = true;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeCurto() {
		return nomeCurto;
	}

	public void setNomeCurto(String nomeCurto) {
		this.nomeCurto = nomeCurto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsPrincipal() {
		return isPrincipal;
	}

	public void setIsPrincipal(Boolean isPrincipal) {
		this.isPrincipal = isPrincipal;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}
}
