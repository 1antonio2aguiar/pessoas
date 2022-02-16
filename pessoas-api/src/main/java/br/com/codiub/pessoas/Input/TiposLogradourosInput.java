package br.com.codiub.pessoas.Input;

import lombok.Data;

@Data
public class TiposLogradourosInput {
	private String sigla;
	
	public String getnome() {
		return sigla == null ? null :sigla.toUpperCase();
	}
}
