package br.com.codiub.pessoas.filter;

import java.sql.Date;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class DadosPfFilter {
	
	private BigDecimal id;
	private String cpf;
	private Long localNascimento;
	private String mae;
	private String pai;
	private Date dataRegistro;
}
