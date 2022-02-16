package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class DocumentosFilter {
	
	private Long id;
	private String numeroDocumento;
	private PessoasFilter pessoasFilter = new PessoasFilter();

}
