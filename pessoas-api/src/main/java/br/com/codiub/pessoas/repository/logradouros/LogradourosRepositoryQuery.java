package br.com.codiub.pessoas.repository.logradouros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.Logradouros;
import br.com.codiub.pessoas.filter.LogradourosFilter;

public interface LogradourosRepositoryQuery {
	
	public Page<Logradouros> filtrar(LogradourosFilter logradourosFilter, Pageable pageable);

}
