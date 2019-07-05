package br.edu.ifpe.missao06.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Funcionario {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_funcionario")
	private Integer id;
	private String matricula;
	private String nome;
	private Empresa empresa;
}
