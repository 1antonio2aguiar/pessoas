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
@Table(name = "PES_LOGRADOUROS", schema = "DBO_CC_PESSOAS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class LogradourosOrigem {

	@EmbeddedId
	private LogradourosOrigemId Id;
	
	private String tipo_logradouro;
	private String nome;
	
}
