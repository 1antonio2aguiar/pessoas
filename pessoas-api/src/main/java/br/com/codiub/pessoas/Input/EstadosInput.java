package br.com.codiub.pessoas.Input;

import lombok.Data;

@Data
public class EstadosInput {
	private String sigla;
	
	public String getsigla() {
		return sigla == null ? null :sigla.toUpperCase();
	}
}
