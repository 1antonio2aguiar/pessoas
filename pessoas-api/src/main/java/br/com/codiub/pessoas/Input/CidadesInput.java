package br.com.codiub.pessoas.Input;

import lombok.Data;

@Data
public class CidadesInput {
	private String nome;
	
	EstadosInput estados = new EstadosInput();
	
	public String getNome() {
		return nome == null ? null :nome.toUpperCase();
	}
}
