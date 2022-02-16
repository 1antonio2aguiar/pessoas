package br.com.codiub.pessoas.entity;
// Generated 3 de mai. de 2021 14:41:28 by Hibernate Tools 4.3.5.Final

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Logradouros generated by hbm2java
 */
@Data
@Entity
@Table(name = "LOGRADOUROS", schema = "DBO_CC_PESSOAS")
public class Logradouros implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String nome;
	private String preposicao;
	private String tituloPatente;
	private String nomeReduzido;
	private String nomeSimplificado;
	private String complemento;
	private String usuario;
	private Date dataAlteracao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRITO", nullable = false)
	private Distritos distritos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPO_LOGRADOURO", nullable = false)
	private TiposLogradouros tiposLogradouros;

}
