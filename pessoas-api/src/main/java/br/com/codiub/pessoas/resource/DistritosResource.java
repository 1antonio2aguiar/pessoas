package br.com.codiub.pessoas.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.entity.Distritos;
import br.com.codiub.pessoas.filter.DistritosFilter;
import br.com.codiub.pessoas.repository.DistritosRepository;

@RestController
@RequestMapping("/distritos")
public class DistritosResource {
	
	@Autowired
	private DistritosRepository distritosRepository;
	
	@GetMapping
	public Page<Distritos> pesquisar(DistritosFilter distritosFilter, Pageable pageable) {
		return distritosRepository.filtrar(distritosFilter, pageable);
	}	
}
