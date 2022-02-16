package br.com.codiub.pessoas.filter;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class DadosPfGeralFilter {
	
	private BigDecimal id;
	private String cpf;
	private Long localNascimento;
	private String mae;
	private String pai;
	private Date dataRegistro;

}
