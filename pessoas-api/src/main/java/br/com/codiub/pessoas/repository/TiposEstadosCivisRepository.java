package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.TiposEstadosCivis;
import br.com.codiub.pessoas.repository.tiposEstadosCivis.TiposEstadosCivisRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposEstadosCivisRepository
    extends JpaRepository<TiposEstadosCivis, Long>, TiposEstadosCivisRepositoryQuery {}
