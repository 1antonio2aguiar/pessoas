package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class ContatosFilter {
	
	private Long id;
	private String contato;
	private PessoasFilter pessoasFilter = new PessoasFilter();

}
