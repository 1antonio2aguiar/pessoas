package br.com.codiub.pessoas.repository.tiposEnderecos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.TiposEnderecos;
import br.com.codiub.pessoas.filter.TiposEnderecosFilter;

public interface TiposEnderecosRepositoryQuery {

	public Page<TiposEnderecos> filtrar(
		TiposEnderecosFilter tiposEnderecosFilter, Pageable pageable);

}