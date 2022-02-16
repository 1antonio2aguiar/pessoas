package br.com.codiub.pessoas.Input;

import javax.persistence.Id;

import br.com.codiub.pessoas.entity.Pessoas;
import lombok.Data;

@Data
public class EnderecoPorPessoaInput {
	
	@Id
	private Long id;
	private Long cep;
	private Long numero;
	private String complemento;
	
	Pessoas pessoas = new Pessoas();
	
	public String getComplemento() {
		return complemento == null ? null :complemento.toUpperCase();
	}

}
