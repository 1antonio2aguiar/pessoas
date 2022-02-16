package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class CepsFilter {
  
	private String cep;
	private Long id;
	private String identificacao;
	private Long numeroFim;
	private Long numeroIni;
	private String usuario;

}
