package br.com.codiub.pessoas.repository.dadosPj;

import br.com.codiub.pessoas.entity.DadosPj;
import br.com.codiub.pessoas.filter.DadosPjFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DadosPjRepositoryQuery {

  public Page<DadosPj> filtrar(DadosPjFilter dadosPjFilter, Pageable pageable);
}
