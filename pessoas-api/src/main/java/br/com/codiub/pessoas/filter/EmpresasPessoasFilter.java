package br.com.codiub.pessoas.filter;

import javax.persistence.Id;
import lombok.Data;

@Data
public class EmpresasPessoasFilter {
	
	@Id
	private Long id;
	private Long vinculo;
	private Long participacao;
	
	private PessoasGeralFilter pessoasGeralFilter = new PessoasGeralFilter();

}
