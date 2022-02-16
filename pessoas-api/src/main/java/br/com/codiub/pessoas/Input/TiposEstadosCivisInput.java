package br.com.codiub.pessoas.Input;

import org.hibernate.validator.constraints.Length;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class TiposEstadosCivisInput {
	private Long id;
	
	@NotNull
	@Length(min = 3)
	private String descricao;
	private String sigla;
	
	public String getDescricao() {
		return descricao == null ? null :descricao.toUpperCase();
	}
	
	public String getSigla() {
		return sigla == null ? null :sigla.toUpperCase();
	}
	
}

