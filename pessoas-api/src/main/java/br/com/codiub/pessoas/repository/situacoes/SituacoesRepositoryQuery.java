package br.com.codiub.pessoas.repository.situacoes;

import br.com.codiub.pessoas.entity.Situacoes;
import br.com.codiub.pessoas.filter.SituacoesFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SituacoesRepositoryQuery {

  public Page<Situacoes> filtrar(SituacoesFilter situacoesFilter, Pageable pageable);
}
