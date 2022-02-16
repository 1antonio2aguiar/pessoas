package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.TiposEmpresas;
import br.com.codiub.pessoas.repository.tiposEmpresas.TiposEmpresasRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposEmpresasRepository
    extends JpaRepository<TiposEmpresas, Long>, TiposEmpresasRepositoryQuery {}
