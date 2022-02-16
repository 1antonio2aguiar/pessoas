package br.com.codiub.pessoas.filter;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TiposLogradourosFilter {
	
	@NotNull
	@Size(min = 0, max = 5)
	private Long id;
	
	@NotNull
	@Size(min = 0, max = 60)
	private String descricao;
	
	@NotNull
	@Size(min = 0, max = 20)
	private String sigla;

}
