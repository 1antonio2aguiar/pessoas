package br.com.codiub.pessoas.Input;

import br.com.codiub.pessoas.entity.TiposVinculosEmpresas;
import lombok.Data;

@Data
public class EmpresasPessoasInput {
	
	private Long id;
	private Long participacao;
	
	private Long idEmpresa;
	private Long idPessoa;
	
	TiposVinculosEmpresas tiposVinculosEmpresas = new TiposVinculosEmpresas();
	
	PessoasInput idPessoas = new PessoasInput();
	PessoasInput idEmpresas = new PessoasInput();

}
