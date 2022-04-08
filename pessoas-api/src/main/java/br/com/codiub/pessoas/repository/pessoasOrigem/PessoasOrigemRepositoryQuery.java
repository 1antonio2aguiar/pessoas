package br.com.codiub.pessoas.repository.pessoasOrigem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.entity.PessoasOrigem;
import br.com.codiub.pessoas.filter.PessoasFilter;
import br.com.codiub.pessoas.filter.PessoasOrigemFilter;

public interface PessoasOrigemRepositoryQuery {
	
	public Page<PessoasOrigem> filtrar(PessoasOrigemFilter pessoasOrigemFilter, Pageable pageable);
	//public Specification<PessoasOrigem> filtrar(PessoasOrigemFilter pessoasOrigemFilter, Pageable pageable);

}