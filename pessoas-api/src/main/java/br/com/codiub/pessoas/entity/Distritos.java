package br.com.codiub.pessoas.entity;
// Generated 3 de mai. de 2021 14:41:28 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Distritos generated by hbm2java
 */
@Data
@Entity
@Table(name = "DISTRITOS", schema = "DBO_CC_PESSOAS")
public class Distritos implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6069398272156839502L;
	@Id
	@Column(name = "ID")
	private Long id;
	private String nome;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CIDADE")
	private Cidades cidades;
	
	public Cidades getCidades() {
		return this.cidades;
	}

	public void setCidades(Cidades cidades) {
		this.cidades = cidades;
	}

	
}
