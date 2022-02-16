package br.com.codiub.pessoas.Input;

import lombok.Data;

@Data
public class CepsInput {
	private Long id;
	private String cep;
	
	public String getcep() {
		return cep == null ? null :cep.toUpperCase();
	}

}
