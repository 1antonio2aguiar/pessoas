package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class DadosPjFilter {
	private String cnpj;
	private String conjuge;
	private String nomeFantasia;
	private String objetoSocial;
	private Long tipoEmpresa;
  
}

