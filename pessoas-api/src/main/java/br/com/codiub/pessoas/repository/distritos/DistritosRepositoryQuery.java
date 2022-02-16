package br.com.codiub.pessoas.repository.distritos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.Distritos;
import br.com.codiub.pessoas.filter.DistritosFilter;

public interface DistritosRepositoryQuery {
	public Page<Distritos> filtrar(DistritosFilter distritosFilter, Pageable pageable) ;
}
