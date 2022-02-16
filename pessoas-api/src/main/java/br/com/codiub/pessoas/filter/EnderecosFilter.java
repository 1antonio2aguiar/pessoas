package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class EnderecosFilter {
	
	private Long id;
	private PessoasFilter pessoasFilter = new PessoasFilter();
	
}
