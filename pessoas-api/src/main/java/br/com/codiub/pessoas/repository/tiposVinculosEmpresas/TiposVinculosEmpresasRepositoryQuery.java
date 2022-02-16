package br.com.codiub.pessoas.repository.tiposVinculosEmpresas;

import br.com.codiub.pessoas.entity.TiposVinculosEmpresas;
import br.com.codiub.pessoas.filter.TiposVinculosEmpresasFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TiposVinculosEmpresasRepositoryQuery {

  public Page<TiposVinculosEmpresas> filtrar(
      TiposVinculosEmpresasFilter tiposVinculosEmpresasFilter, Pageable pageable);
}
