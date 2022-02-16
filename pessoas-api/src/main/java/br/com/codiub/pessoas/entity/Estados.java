package br.com.codiub.pessoas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ESTADOS", schema = "DBO_CC_PESSOAS")
public class Estados implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4037927486045828661L;
	@Id
	@Column(name = "ID")
	private Long id;
	private String nome;
	private String sigla;
	
	
}
