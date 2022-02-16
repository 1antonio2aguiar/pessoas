package br.com.codiub.pessoas.repository.tiposLogradouros;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.TiposLogradouros;
import br.com.codiub.pessoas.filter.TiposLogradourosFilter;

public interface TiposLogradourosRepositoryQuery {
	
	public Page<TiposLogradouros> filtrar(
		TiposLogradourosFilter tiposLogradourosFilter, Pageable pageable);
		public List<TiposLogradouros> filtrar(TiposLogradourosFilter tiposLogradourosFilter);

}
