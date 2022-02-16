package br.com.codiub.pessoas.repository.tiposEstadosCivis;

import br.com.codiub.pessoas.entity.TiposEstadosCivis;
import br.com.codiub.pessoas.filter.TiposEstadosCivisFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TiposEstadosCivisRepositoryQuery {

  public Page<TiposEstadosCivis> filtrar(
      TiposEstadosCivisFilter tiposEstadosCivisFilter, Pageable pageable);
}
