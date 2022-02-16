package br.com.codiub.pessoas.Input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistritosInput {
	
	private Long id;
	private String nome;
	
	CidadesInput cidades = new CidadesInput();
	
	public String getNome() {
		return nome == null ? null :nome.toUpperCase();
	}

}
