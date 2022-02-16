package br.com.codiub.pessoas.repository.tiposRelacionamentos;

import br.com.codiub.pessoas.entity.TiposRelacionamentos;
import br.com.codiub.pessoas.filter.TiposRelacionamentosFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TiposRelacionamentosRepositoryQuery {

  public Page<TiposRelacionamentos> filtrar(
      TiposRelacionamentosFilter tiposRelacionamentosFilter, Pageable pageable);
}
