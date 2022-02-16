package br.com.codiub.pessoas.Input;

import javax.persistence.Id;

import br.com.codiub.pessoas.entity.TiposEmpresas;
import lombok.Data;

@Data
public class DadosPjInput {
	
	@Id
	private Long id;
	private String cnpj;
	private String nomeFantasia;
	private String objetoSocial;
	private String microEmpresa;
	private String conjuge;
	
	private TiposEmpresas tiposEmpresas = new TiposEmpresas();
	
}
