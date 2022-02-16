package br.com.codiub.pessoas.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.entity.TiposEnderecos;
import br.com.codiub.pessoas.filter.TiposEnderecosFilter;
import br.com.codiub.pessoas.repository.TiposEnderecosRepository;

@RestController
@RequestMapping("/tiposEnderecos")
public class TiposEnderecosResource {
	
	@Autowired private TiposEnderecosRepository tiposEnderecosRepository;
	
	@GetMapping
	public Page<TiposEnderecos> pesquisar(TiposEnderecosFilter tiposEnderecosFilter, Pageable pageable) {
		return tiposEnderecosRepository.filtrar(tiposEnderecosFilter, pageable);
	}

}
