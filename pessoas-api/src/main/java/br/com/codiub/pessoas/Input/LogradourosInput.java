package br.com.codiub.pessoas.Input;

import javax.persistence.Id;
import lombok.Data;

@Data
public class LogradourosInput {
	@Id
	private Long id;
	private String nome;
	
	TiposLogradourosInput tiposLogradouros = new TiposLogradourosInput();
	
	public String getnome() {
		return nome == null ? null :nome.toUpperCase();
	}

}
