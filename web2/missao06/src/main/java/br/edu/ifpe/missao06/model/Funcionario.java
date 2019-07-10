package br.edu.ifpe.missao06.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Funcionario {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "funcionario_id")
	private Integer id;

	@NotBlank(message = "Informe a matricula") @Size(max = 8, message = "Matricula não pode ter mais de 8 caracteres")
	@Column(name = "funcionario_matricula", length = 8, nullable = false)
	private String matricula;

	@NotBlank(message = "Informe o nome") @Size(max = 70, message = "Nome não pode ter mais de 70 caracteres")
	@Column(name = "funcionario_nome", length = 70, nullable = false)
	private String nome;

	@NotBlank(message = "Informe o cpf") @Size(max = 11, message = "CPF não pode ter mais de 11 caracteres")
	@CPF
	@Column(name = "funcionario_cpf", length = 11, nullable = false, unique = true)
	private String cpf;

	@DateTimeFormat(pattern = "dd/MM/yyyy") @Past(message = "Data de nascimento informada inválida")
	@Column(name = "funcionario_data_nascimento")
	private LocalDate dataNascimento;

	@NotNull
	@ManyToOne @JoinColumn(name = "funcionario_cargo")
	private Cargo cargo;

	@NotNull
	@OneToOne @JoinColumn(name = "funcionario_empresa")
	private Empresa empresa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
