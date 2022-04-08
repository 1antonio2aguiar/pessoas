package br.com.codiub.pessoas.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "PES_CIDADES", schema = "DBO_CC_PESSOAS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CidadesOrigem {
	@Id
	private Long cidade;
	private String nome;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTADO")
	private EstadosOrigem estadosOrigem = new EstadosOrigem();
}
