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

import br.com.codiub.pessoas.Input.EnderecosInput;
import br.com.codiub.pessoas.entity.Enderecos;
import br.com.codiub.pessoas.filter.EnderecosFilter;
import br.com.codiub.pessoas.repository.EnderecosRepository;
import br.com.codiub.pessoas.service.EnderecosService;

@RestController
@RequestMapping("/enderecos")
public class EnderecosResource {
	
	@Autowired private EnderecosRepository enderecosRepository;
	@Autowired private EnderecosService enderecosService;
	
	//Tonhas 17082021 - insert
	@PostMapping("/novo")
	public ResponseEntity<Object> criar(@RequestBody EnderecosInput enderecosInput, HttpServletResponse response) {
		ResponseEntity<Object> resp = enderecosService.save(enderecosInput);
		return resp;
	}
	
	//AA - 02/09/2021 Update
	@PutMapping("/{codigo}")
	public ResponseEntity<Enderecos> atualizar(@PathVariable Long codigo, @Validated @RequestBody EnderecosInput enderecosInput) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
		
		Enderecos enderecosSalva = enderecosService.atualizar(codigo, enderecosInput);
	    return ResponseEntity.ok(enderecosSalva);
	}
	
	@GetMapping
	public Page<Enderecos> pesquisar(EnderecosFilter enderecosFilter, Pageable pageable) {
		
		//System.err.println("BATEU AQUI OLHA A BAGAÇA!! "); 
		
	    return enderecosRepository.filtrar(enderecosFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Enderecos> buscarPeloCodigo(@PathVariable Long codigo) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
	    Optional<Enderecos> enderecos = enderecosRepository.findById(codigo);
	    return enderecos != null
	        ? ResponseEntity.ok(enderecos.get())
	        : ResponseEntity.notFound().build();
	}
	
	//Tonhas busca lista de enderecos por pessoa
	@GetMapping(value = "findByPessoasId")
	@ResponseBody
	public ResponseEntity<List<Enderecos>> findByPessoasId(@RequestParam(name = "codigo") Long codigo){
		//System.err.println("Bateu aqui " + codigo);
		List<Enderecos> enderecos = enderecosRepository.findByPessoasId(codigo);
		return new ResponseEntity<List<Enderecos>>(enderecos, HttpStatus.OK);
	}
	
	// Tonhas - Delete
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		//System.err.println("BATEU AQUI !! "); 
		enderecosRepository.deleteById(codigo);
	}
}
