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

import br.com.codiub.pessoas.Input.DocumentosInput;
import br.com.codiub.pessoas.entity.Documentos;
import br.com.codiub.pessoas.filter.DocumentosFilter;
import br.com.codiub.pessoas.repository.DocumentosRepository;
import br.com.codiub.pessoas.service.DocumentosService;

@RestController
@RequestMapping("/documentos")
public class DocumentosResource {
	
	@Autowired private DocumentosRepository documentosRepository;
	@Autowired private DocumentosService documentosService;	
	
	//Tonhas 13092021 - insert
	@PostMapping("/novo")
	public ResponseEntity<Object> criar(@RequestBody DocumentosInput documentosInput, HttpServletResponse response) {
		ResponseEntity<Object> resp = documentosService.save(documentosInput);
		return resp;
	}
	
	//AA - 02/09/2021 Update
	@PutMapping("/{codigo}")
	public ResponseEntity<Documentos> atualizar(@PathVariable Long codigo, @Validated @RequestBody DocumentosInput documentosInput) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
		
		Documentos documentosSalva = documentosService.atualizar(codigo, documentosInput);
	    return ResponseEntity.ok(documentosSalva);
	}
	
	@GetMapping
	public Page<Documentos> pesquisar(DocumentosFilter documentosFilter, Pageable pageable) {
	    return documentosRepository.filtrar(documentosFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Documentos> buscarPeloCodigo(@PathVariable Long codigo) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
	    Optional<Documentos> documentos = documentosRepository.findById(codigo);
	    return documentos != null
	        ? ResponseEntity.ok(documentos.get())
	        : ResponseEntity.notFound().build();
	}
	
	//Tonhas busca lista de documentos por pessoa
	@GetMapping(value = "findByPessoasId")
	@ResponseBody
	public ResponseEntity<List<Documentos>> findByPessoasId(@RequestParam(name = "codigo") Long codigo){
		//System.err.println("Bateu aqui " + codigo);
		List<Documentos> documentos = documentosRepository.findByPessoasId(codigo);
		return new ResponseEntity<List<Documentos>>(documentos, HttpStatus.OK);
	}
	
	// Tonhas - Delete
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		//System.err.println("BATEU AQUI !! "); 
		documentosRepository.deleteById(codigo);
	}

}
