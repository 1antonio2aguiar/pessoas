package br.com.codiub.pessoas.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.entity.PessoasOrigem;
import br.com.codiub.pessoas.filter.PessoasOrigemFilter;
import br.com.codiub.pessoas.repository.PessoasOrigemRepository;
import br.com.codiub.pessoas.repository.pessoasOrigem.PessoasOrigemRepositoryImpl;


@RestController
@RequestMapping("/pessoasOrigem")
public class PessoasOrigemResource {

	@Autowired private PessoasOrigemRepository pessoasOrigemRepository;
	//@Autowired private PessoasOrigemDanielRepository pessoasOrigemDanielRepository;
	
	// Aqui busca os dados para gerar a lista.
	@GetMapping
	public Page<PessoasOrigem> pesquisar(PessoasOrigemFilter pessoasOrigemFilter, Pageable pageable) {
		//System.err.println("OLHA ONDE ELE VÊIO !! NENEM " );
		
		//Specification<PessoasOrigem> pessoasOrigemImpl = PessoasOrigemRepositoryImpl.filtrar(pessoasOrigemFilter, pageable);
		//Page<PessoasOrigem> pessoaPage = pessoasOrigemRepository.findAll(pessoasOrigemImpl, pageable);
		
		//pessoaPage.getContent().forEach(pessoaOrigem -> {,});
		
	    return pessoasOrigemRepository.filtrar(pessoasOrigemFilter, pageable);
	}
	 
	/*@GetMapping("/spec")
	public Page<PessoasOrigem> readPage(PessoasOrigemFilter pessoasOrigemFilter, Pageable pageable) {
		System.err.println("OLHA ONDE ELE VÊIO !! NENEM " );
		
		Specification<PessoasOrigem> pessoasOrigemSpec = PessoasOrigemSpec.filtrar(pessoasOrigemFilter, pageable);
		Page<PessoasOrigem> pessoaPage = pessoasOrigemDanielRepository.findAll(pessoasOrigemSpec, pageable);
		
		pessoaPage.getContent().forEach(pessoaOrigem -> {
			
		});
		
	    //return pessoasOrigemRepository.filtrar(pessoasOrigemFilter, pageable);
	    return pessoasOrigemDanielRepository.filtrar(pessoasOrigemFilter, pageable);
	}*/

}
