package br.com.codiub.pessoas.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.entity.PessoasGeral;
import br.com.codiub.pessoas.filter.PessoasGeralFilter;
import br.com.codiub.pessoas.repository.PessoasGeralRepository;

@RestController
@RequestMapping("/pessoasGeral")
public class PessoasGeralResource {
	
	@Autowired private PessoasGeralRepository pessoasGeralRepository;
	
	@GetMapping
	public Page<PessoasGeral> pesquisar(PessoasGeralFilter pessoasGeralFilter, Pageable pageable) {
		
		//System.err.println("Entrou na bagaça!@$? ");
		
		return pessoasGeralRepository.filtrar(pessoasGeralFilter, pageable);
	}
	
}
