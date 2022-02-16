package br.com.codiub.pessoas.repository.pessoas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.filter.PessoasFilter;

public interface PessoasRepositoryQuery {

  public Page<Pessoas> filtrar(PessoasFilter pessoasFilter, Pageable pageable);
  
}
