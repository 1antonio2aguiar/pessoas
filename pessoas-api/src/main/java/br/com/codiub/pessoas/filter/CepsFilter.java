package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class CepsFilter {
  
	private String cep;
	private Long id;
	private Long numeroIni;
	private Long numeroFim;
	private String identificacao;
	private String usuario;

}
