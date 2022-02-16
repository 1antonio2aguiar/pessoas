package br.com.codiub.pessoas.repository.tiposEmpresas;

import br.com.codiub.pessoas.entity.TiposEmpresas;
import br.com.codiub.pessoas.filter.TiposEmpresasFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TiposEmpresasRepositoryQuery {

  public Page<TiposEmpresas> filtrar(TiposEmpresasFilter tiposEmpresasFilter, Pageable pageable);
}
