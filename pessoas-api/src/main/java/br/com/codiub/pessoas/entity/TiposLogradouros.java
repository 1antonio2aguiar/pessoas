package br.com.codiub.pessoas.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "TIPOS_LOGRADOUROS", schema = "DBO_CC_ENDERECOS")
public class TiposLogradouros implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String descricao;
	private String sigla;

}
