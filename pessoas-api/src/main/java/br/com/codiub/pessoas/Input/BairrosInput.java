package br.com.codiub.pessoas.Input;

import javax.persistence.Id;

import lombok.Data;

@Data
public class BairrosInput {
	@Id
	private Long id;
	private String nome;
	
	public String getnome() {
		return nome == null ? null :nome.toUpperCase();
	}

}
