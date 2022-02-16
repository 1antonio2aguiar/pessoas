package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.TiposVinculosEmpresas;
import br.com.codiub.pessoas.repository.tiposVinculosEmpresas.TiposVinculosEmpresasRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposVinculosEmpresasRepository
    extends JpaRepository<TiposVinculosEmpresas, Long>, TiposVinculosEmpresasRepositoryQuery {}
