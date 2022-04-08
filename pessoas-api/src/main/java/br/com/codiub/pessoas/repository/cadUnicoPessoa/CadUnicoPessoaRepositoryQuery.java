package br.com.codiub.pessoas.repository.cadUnicoPessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.CadUnicoPessoa;
import br.com.codiub.pessoas.filter.CadUnicoPessoaFilter;

public interface CadUnicoPessoaRepositoryQuery {
	
	public Page<CadUnicoPessoa> filtrar(CadUnicoPessoaFilter cadUnicoPessoaFilter, Pageable pageable);

}
