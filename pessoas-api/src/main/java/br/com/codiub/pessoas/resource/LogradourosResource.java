package br.com.codiub.pessoas.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.entity.Logradouros;
import br.com.codiub.pessoas.filter.LogradourosFilter;
import br.com.codiub.pessoas.repository.LogradourosRepository;

@RestController
@RequestMapping("/logradouros")
public class LogradourosResource {
	
	@Autowired private LogradourosRepository logradourosRepository;
	
	@GetMapping
	public Page<Logradouros> pesquisar(LogradourosFilter logradourosFilter, Pageable pageable) {
	    return logradourosRepository.filtrar(logradourosFilter, pageable);
	}	

}
