package br.com.codiub.pessoas.repository.ceps;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.Ceps;
import br.com.codiub.pessoas.filter.CepsFilter;

public interface CepsRepositoryQuery {
	
	public Page<Ceps> filtrar(CepsFilter cepsFilter, Pageable pageable);

}
