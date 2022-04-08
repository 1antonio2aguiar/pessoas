package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class CepsOrigemFilter {
	
	private Long cidade;
	private Long distrito;
	private Long logradouro;
	private Long numeroIni;
	private Long numeroFim;
	private Long cep;
	private String identificacao;
	
}
