package br.com.codiub.pessoas.repository.empresasPessoas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.codiub.pessoas.entity.EmpresasPessoas;
import br.com.codiub.pessoas.filter.EmpresasPessoasFilter;

public interface EmpresasPessoasRepositoryQuery {
	
	public Page<EmpresasPessoas> filtrar(EmpresasPessoasFilter empresasPessoasFilter, Pageable pageable);

}

