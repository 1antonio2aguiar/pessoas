package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class DistritosFilter {
	
	private Long Id;
	private String Nome;
	
	private CidadesFilter cidadesFilter = new CidadesFilter();

}
