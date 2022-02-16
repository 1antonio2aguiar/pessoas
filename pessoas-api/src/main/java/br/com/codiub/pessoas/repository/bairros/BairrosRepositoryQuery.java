package br.com.codiub.pessoas.repository.bairros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.Bairros;
import br.com.codiub.pessoas.filter.BairrosFilter;

public interface BairrosRepositoryQuery {
	public Page<Bairros> filtrar(BairrosFilter bairrosFilter, Pageable pageable);
}
