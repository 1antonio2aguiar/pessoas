package br.com.codiub.pessoas.repository.PessoasGeral;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.PessoasGeral;
import br.com.codiub.pessoas.filter.PessoasGeralFilter;

public interface PessoasGeralRepositoryQuery {
	
	public Page<PessoasGeral> filtrar(PessoasGeralFilter pessoasGeralFilter, Pageable pageable);

}
