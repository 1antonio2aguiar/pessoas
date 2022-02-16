package br.com.codiub.pessoas.repository.profissoes;

import br.com.codiub.pessoas.entity.Profissoes;
import br.com.codiub.pessoas.filter.ProfissoesFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfissoesRepositoryQuery {

  public Page<Profissoes> filtrar(ProfissoesFilter profissoesFilter, Pageable pageable);
}
