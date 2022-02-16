package br.com.codiub.pessoas.repository.documentos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.Documentos;
import br.com.codiub.pessoas.filter.DocumentosFilter;

public interface DocumentosRepositoryQuery {
	public Page<Documentos> filtrar(DocumentosFilter documentosFilter, Pageable pageable);
}
