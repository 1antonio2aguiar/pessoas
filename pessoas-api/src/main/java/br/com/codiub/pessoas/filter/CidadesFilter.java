package br.com.codiub.pessoas.filter;

import lombok.Data;

@Data
public class CidadesFilter {
	
	private Long Id;
	private String Nome;
	
	private EstadosFilter estadosFilter = new EstadosFilter();
	
}
