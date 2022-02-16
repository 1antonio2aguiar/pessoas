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
 * Situacoes generated by hbm2java
 */
@Entity
@Table(name = "SITUACOES", schema = "DBO_CC_PESSOAS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Situacoes implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -946453416660802037L;
	private Long id;
	private String descricao;

	@Id
	@GeneratedValue(generator = "SEQ_SITUACOES")
	@SequenceGenerator(name = "SEQ_SITUACOES", sequenceName = "SEQ_SITUACOES", allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 5, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DESCRICAO", nullable = false, length = 100)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}