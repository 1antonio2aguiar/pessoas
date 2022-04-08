package br.com.codiub.pessoas.filter;

import java.util.Date;
import javax.persistence.Id;
import lombok.Data;

@Data
public class PessoasOrigemFilter {
	
	@Id
	private Long   pessoa             ; 
	
	private Long   cgc_cpf            ; 
	private Long   tipo_pessoa        ;
	private Long   numero			  ;
	private Long   cep                ; 
	private Long   tipo_empresa       ; 
	private String nome               ; 
	private Date   data_nascimento    ; 
	private String cpfCnpj;
	
	CepsOrigemFilter cepsOrigemFilter = new CepsOrigemFilter();
}
