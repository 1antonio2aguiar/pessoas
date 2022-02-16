package br.com.codiub.pessoas.repository.dadosPf;

import br.com.codiub.pessoas.entity.DadosPf;
import br.com.codiub.pessoas.filter.DadosPfFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DadosPfRepositoryQuery {

  public Page<DadosPf> filtrar(DadosPfFilter dadosPfFilter, Pageable pageable);
}
