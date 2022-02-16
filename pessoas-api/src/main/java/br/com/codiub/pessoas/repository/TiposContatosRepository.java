package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.TiposContatos;
import br.com.codiub.pessoas.repository.tiposContatos.TiposContatosRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposContatosRepository
    extends JpaRepository<TiposContatos, Long>, TiposContatosRepositoryQuery {}
