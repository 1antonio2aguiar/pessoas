package br.com.codiub.pessoas.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.entity.Bairros;
import br.com.codiub.pessoas.filter.BairrosFilter;
import br.com.codiub.pessoas.repository.BairrosRepository;

@RestController
@RequestMapping("/bairros")
public class BairrosResource {
	
	@Autowired private BairrosRepository bairrosRepository;
	
	@GetMapping
	public Page<Bairros> pesquisar(BairrosFilter bairrosFilter, Pageable pageable) {
	    return bairrosRepository.filtrar(bairrosFilter, pageable);
	}

}
