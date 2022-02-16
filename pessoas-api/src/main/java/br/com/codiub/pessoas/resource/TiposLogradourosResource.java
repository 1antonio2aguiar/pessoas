package br.com.codiub.pessoas.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.entity.TiposLogradouros;
import br.com.codiub.pessoas.filter.TiposLogradourosFilter;
import br.com.codiub.pessoas.repository.TiposLogradourosRepository;

@RestController
@RequestMapping("/tiposLogradouros")
public class TiposLogradourosResource {
	
	@Autowired private TiposLogradourosRepository tiposLogradourosRepository;
	
	@GetMapping
	public Page<TiposLogradouros> pesquisar(TiposLogradourosFilter tiposLogradourosFilter, Pageable pageable) {
		return tiposLogradourosRepository.filtrar(tiposLogradourosFilter, pageable);
	}
	
	@GetMapping("/list")
		public List<TiposLogradouros> pesquisar(TiposLogradourosFilter tiposLogradourosFilter) {
		return tiposLogradourosRepository.filtrar(tiposLogradourosFilter);
	}
}
