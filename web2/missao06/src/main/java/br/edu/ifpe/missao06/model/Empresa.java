package br.edu.ifpe.missao06.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empresa_id")
	private Integer id;

	@Column(name = "empresa_nome", length = 50, nullable = false)
	private String nome;

	@Column(name = "empresa_nome_curto", length = 10)
	private String nomeCurto;

	@Column(name = "empresa_email", length = 80)
	private String email;

	@Column(name = "empresa_is_principal", nullable = false)
	private boolean is_principal;

	@Column(name = "empresa_situacao", nullable = false)
	private boolean situacao = true;

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

	public boolean isIs_principal() {
		return is_principal;
	}

	public void setIs_principal(boolean is_principal) {
		this.is_principal = is_principal;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}
}
