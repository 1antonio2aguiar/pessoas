package br.com.codiub.pessoas.resource;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.Input.TiposDocumentosInput;
import br.com.codiub.pessoas.entity.TiposDocumentos;
import br.com.codiub.pessoas.filter.TiposDocumentosFilter;
import br.com.codiub.pessoas.repository.TiposDocumentosRepository;
import br.com.codiub.pessoas.service.TiposDocumentosService;

@RestController
@RequestMapping("/tiposDocumentos")
public class TiposDocumentosResource {

  @Autowired private TiposDocumentosRepository tiposDocumentosRepository;

  @Autowired private TiposDocumentosService tiposDocumentosService;
  
  //AA - Inserir
  @PostMapping
  public ResponseEntity<TiposDocumentos> criar(@RequestBody TiposDocumentosInput tiposDocumentosInput, HttpServletResponse response) {
	  TiposDocumentos tiposDocumentosSalva = tiposDocumentosService.save(tiposDocumentosInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(tiposDocumentosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<TiposDocumentos> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TiposDocumentos> tiposDocumentos = tiposDocumentosRepository.findById(codigo);
    return tiposDocumentos != null
        ? ResponseEntity.ok(tiposDocumentos.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    tiposDocumentosRepository.deleteById(codigo);
  }
  
  //AA - Update
  @PutMapping("/{codigo}")
	public ResponseEntity<TiposDocumentos> atualizar(@PathVariable Long codigo,@Validated @RequestBody TiposDocumentosInput tiposDocumentosInput) {
	  TiposDocumentos tiposDocumentosSalva = tiposDocumentosService.atualizar(codigo, tiposDocumentosInput);
	return ResponseEntity.ok(tiposDocumentosSalva);
  }

  @GetMapping
  public Page<TiposDocumentos> pesquisar(
      TiposDocumentosFilter tiposDocumentosFilter, Pageable pageable) {
    return tiposDocumentosRepository.filtrar(tiposDocumentosFilter, pageable);
  }
}
