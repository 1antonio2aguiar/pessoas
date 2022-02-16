package br.com.codiub.pessoas.Input;

import javax.persistence.Id;

import br.com.codiub.pessoas.entity.TiposEnderecos;
import lombok.Data;

@Data
public class EnderecosInput {
	
	@Id
	private Long id;
	private Long cep;
	private Long numero;
	private String complemento;
	
	private Long pessoa;
	private Long bairro;
	private Long logradouro;
	private String usuario;
	
	TiposEnderecos tiposEnderecos = new TiposEnderecos();
	
	PessoasInput pessoas = new PessoasInput();
	DistritosInput 	 	distritos 	= new DistritosInput();
	BairrosInput 		bairros 	= new BairrosInput();
	CepsInput 			ceps 		= new CepsInput();
	LogradourosInput 	logradouros	= new LogradourosInput();
	
	public String getComplemento() {
		return complemento == null ? null :complemento.toUpperCase();
	}

}
