package br.com.codiub.pessoas.repository.tiposPessoas;

import br.com.codiub.pessoas.entity.TiposPessoas;
import br.com.codiub.pessoas.filter.TiposPessoasFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TiposPessoasRepositoryQuery {

  public Page<TiposPessoas> filtrar(TiposPessoasFilter tiposPessoasFilter, Pageable pageable);
}
