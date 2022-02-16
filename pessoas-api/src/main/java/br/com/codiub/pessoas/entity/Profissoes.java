package br.com.codiub.pessoas.entity;
// Generated 3 de mai. de 2021 14:41:28 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Profissoes generated by hbm2java
 */
@Entity
@Table(name = "PROFISSOES", schema = "DBO_CC_PESSOAS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Profissoes implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7375219208641298242L;
	private Long id;
	private String nome;

	@Id
	@GeneratedValue(generator = "SEQ_PROFISSOES")
	@SequenceGenerator(name = "SEQ_PROFISSOES", sequenceName = "SEQ_PROFISSOES", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NOME", nullable = false)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
