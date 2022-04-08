package br.com.codiub.pessoas.resource;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.Input.CadUnicoPessoaInput;
import br.com.codiub.pessoas.entity.CadUnicoPessoa;
import br.com.codiub.pessoas.entity.PessoasOrigem;
import br.com.codiub.pessoas.filter.CadUnicoPessoaFilter;
import br.com.codiub.pessoas.repository.CadUnicoPessoaRepository;

@RestController
@RequestMapping("/cadUnicoPessoa")
public class CadUnicoPessoaResource {
	
	@Autowired private CadUnicoPessoaRepository cadUnicoPessoaRepository;
	//@Autowired private PessoasOrigemRepository pessoasOrigemRepository;
	
	// Aqui busca os dados para gerar a lista.
	@GetMapping
	public Page<CadUnicoPessoa> pesquisar(CadUnicoPessoaFilter cadUnicoPessoaFilter, Pageable pageable) {
		//System.err.println("OLHA ONDE ELE VÊIO !! NENEM " );
	    return cadUnicoPessoaRepository.filtrar(cadUnicoPessoaFilter, pageable);
	}
	
	//Tonhas edit
	@GetMapping("/{codigo}")
	public ResponseEntity<CadUnicoPessoaInput> findByPessoasCdUnico(@PathVariable Long codigo, CadUnicoPessoaInput cadUnicoPessoaInput) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
		
		Optional<CadUnicoPessoa> cadUnicoPessoa = cadUnicoPessoaRepository.findById(codigo);
		
		if(cadUnicoPessoa.isPresent()) {
			BeanUtils.copyProperties(cadUnicoPessoa.get(), cadUnicoPessoaInput);
			
			// Busca os dados de orrigem desta pesssoa
			//Optional<PessoasOrigem> dadosPessoaOrigem = pessoasOrigemRepository.findById(codigo);
		}
		
		
		return cadUnicoPessoaInput != null ? ResponseEntity.ok(cadUnicoPessoaInput) : ResponseEntity.notFound().build();
	}

}
