package br.com.codiub.pessoas.repository.enderecos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.Enderecos;
import br.com.codiub.pessoas.filter.EnderecosFilter;

public interface EnderecosRepositoryQuery {
	
	public Page<Enderecos> filtrar(EnderecosFilter enderecosFilter, Pageable pageable);

}

