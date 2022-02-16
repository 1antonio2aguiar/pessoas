package br.com.codiub.pessoas.repository.tiposDocumentos;

import br.com.codiub.pessoas.entity.TiposDocumentos;
import br.com.codiub.pessoas.filter.TiposDocumentosFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TiposDocumentosRepositoryQuery {

  public Page<TiposDocumentos> filtrar(
      TiposDocumentosFilter tiposDocumentosFilter, Pageable pageable);
}
