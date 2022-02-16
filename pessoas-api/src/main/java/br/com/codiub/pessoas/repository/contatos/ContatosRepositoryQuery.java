package br.com.codiub.pessoas.repository.contatos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.Contatos;
import br.com.codiub.pessoas.filter.ContatosFilter;

public interface ContatosRepositoryQuery {
	public Page<Contatos> filtrar(ContatosFilter contatosFilter, Pageable pageable);
}
