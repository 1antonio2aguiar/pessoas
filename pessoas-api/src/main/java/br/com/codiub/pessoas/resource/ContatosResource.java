package br.com.codiub.pessoas.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.Input.ContatosInput;
import br.com.codiub.pessoas.entity.Contatos;
import br.com.codiub.pessoas.filter.ContatosFilter;
import br.com.codiub.pessoas.repository.ContatosRepository;
import br.com.codiub.pessoas.service.ContatosService;

@RestController
@RequestMapping("/contatos")
public class ContatosResource {
	
	@Autowired private ContatosRepository contatosRepository;
	@Autowired private ContatosService contatosService;
	
	//Tonhas 13092021 - insert
	@PostMapping("/novo")
	public ResponseEntity<Object> criar(@RequestBody ContatosInput contatosInput, HttpServletResponse response) {
		ResponseEntity<Object> resp = contatosService.save(contatosInput);
		return resp;
	}
	
	//AA - 13/09/2021 Update
	@PutMapping("/{codigo}")
	public ResponseEntity<Contatos> atualizar(@PathVariable Long codigo, @Validated @RequestBody ContatosInput contatosInput) {
		Contatos contatosSalva = contatosService.atualizar(codigo, contatosInput);
	    return ResponseEntity.ok(contatosSalva);
	}
	
	@GetMapping
	public Page<Contatos> pesquisar(ContatosFilter contatosFilter, Pageable pageable) {
		//System.err.println("OLHA ONDE ELE VÊIO !! NENEM " );
	    return contatosRepository.filtrar(contatosFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Contatos> buscarPeloCodigo(@PathVariable Long codigo) {
		//System.err.println("OLHA ONDE ELE VÊIO !! PAPAI " );
	    Optional<Contatos> contatos = contatosRepository.findById(codigo);
	    return contatos != null
	        ? ResponseEntity.ok(contatos.get())
	        : ResponseEntity.notFound().build();
	}
	
	//Tonhas busca lista de contatos por pessoa
	@GetMapping(value = "findByPessoasId")
	@ResponseBody
	public ResponseEntity<List<Contatos>> findByPessoasId(@RequestParam(name = "codigo") Long codigo){
		//System.err.println("Bateu aqui " + codigo);
		List<Contatos> contatos = contatosRepository.findByPessoasId(codigo);
		return new ResponseEntity<List<Contatos>>(contatos, HttpStatus.OK);
	}
	
	// Tonhas - Delete
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		//System.err.println("BATEU AQUI !! "); 
		contatosRepository.deleteById(codigo);
	}

}
