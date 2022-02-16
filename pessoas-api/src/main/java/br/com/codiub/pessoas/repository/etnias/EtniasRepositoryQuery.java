package br.com.codiub.pessoas.repository.etnias;

import br.com.codiub.pessoas.entity.Etnias;
import br.com.codiub.pessoas.filter.EtniasFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EtniasRepositoryQuery {

  public Page<Etnias> filtrar(EtniasFilter etniasFilter, Pageable pageable);
}
