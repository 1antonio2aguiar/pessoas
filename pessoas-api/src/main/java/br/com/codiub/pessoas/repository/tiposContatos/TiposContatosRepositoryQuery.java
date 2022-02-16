package br.com.codiub.pessoas.repository.tiposContatos;

import br.com.codiub.pessoas.entity.TiposContatos;
import br.com.codiub.pessoas.filter.TiposContatosFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TiposContatosRepositoryQuery {

  public Page<TiposContatos> filtrar(TiposContatosFilter tiposContatosFilter, Pageable pageable);
}
