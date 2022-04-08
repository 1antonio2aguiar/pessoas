package br.com.codiub.pessoas.filter;

import java.util.Date;
import javax.persistence.Id;
import lombok.Data;

@Data
public class CadUnicoPessoaFilter {
	
	@Id
	private Long id;
	private Long cdOrigem;
	private String nome;
	private Date dataCadastro;
	private String cpfCnpj;
	private Date dataNascimento;
	private Long pessoasCdUnico;
	
}
