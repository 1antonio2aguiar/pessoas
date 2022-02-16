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

@Data
@Entity
@Table(name = "CIDADES", schema = "DBO_CC_PESSOAS")
public class Cidades implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4511481159854854165L;
	@Id
	@Column(name = "ID")
	private Long id;
	private String nome;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTADO")
	private Estados estados;
	
	public Estados getEstados() {
		return this.estados;
	}
	
	public void setEstados(Estados estados) {
		this.estados = estados;
	}

	
}
