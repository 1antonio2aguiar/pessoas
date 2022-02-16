package br.com.codiub.pessoas.Input;

import javax.persistence.Id;

import br.com.codiub.pessoas.entity.TiposContatos;
import lombok.Data;

@Data
public class ContatosInput {
	
	@Id
	private Long id;
	private String contato;
	private Long pessoa;
	private String usuario;
	
	TiposContatos tiposContatos = new TiposContatos();
	PessoasInput pessoas = new PessoasInput();
	
	public String getContato() {
		return contato == null ? null :contato.toLowerCase();
	}
	

}
