package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.TiposDocumentos;
import br.com.codiub.pessoas.repository.tiposDocumentos.TiposDocumentosRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposDocumentosRepository
    extends JpaRepository<TiposDocumentos, Long>, TiposDocumentosRepositoryQuery {}
