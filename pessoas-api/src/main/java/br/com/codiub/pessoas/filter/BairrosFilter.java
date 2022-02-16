package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class BairrosFilter {
	
	private Long Id;
	private String Nome;
	
	private DistritosFilter distritosFilter = new DistritosFilter();
	
}
