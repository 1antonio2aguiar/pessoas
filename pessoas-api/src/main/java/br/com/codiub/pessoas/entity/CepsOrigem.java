package br.com.codiub.pessoas.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "PES_CEPS", schema = "DBO_CC_PESSOAS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CepsOrigem {
	
	@EmbeddedId
	private CepsOrigemId Id;
	
	private Long cep;
	private Long numero_ini;
	private Long numero_fim;
	private String identificacao;

}
