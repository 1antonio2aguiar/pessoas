package br.com.codiub.pessoas.repository.cepsOrigem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.CepsOrigem;
import br.com.codiub.pessoas.filter.CepsOrigemFilter;


public interface CepsOrigemRepositoryQuery {
	public Page<CepsOrigem> filtrar(CepsOrigemFilter cepsOrigemFilter, Pageable pageable);
}
