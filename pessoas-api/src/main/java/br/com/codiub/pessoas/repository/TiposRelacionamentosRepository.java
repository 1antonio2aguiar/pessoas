package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.TiposRelacionamentos;
import br.com.codiub.pessoas.repository.tiposRelacionamentos.TiposRelacionamentosRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposRelacionamentosRepository
    extends JpaRepository<TiposRelacionamentos, Long>, TiposRelacionamentosRepositoryQuery {}
