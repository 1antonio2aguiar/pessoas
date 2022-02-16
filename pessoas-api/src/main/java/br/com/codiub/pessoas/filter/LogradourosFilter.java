package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class LogradourosFilter {
	
	private Long Id;
	private String Nome;
	
	private DistritosFilter distritosFilter = new DistritosFilter();
	private TiposLogradourosFilter tiposLogradourosFilter = new TiposLogradourosFilter();

}
