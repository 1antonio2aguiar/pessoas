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

import br.com.codiub.pessoas.Input.TiposContatosInput;
import br.com.codiub.pessoas.entity.TiposContatos;
import br.com.codiub.pessoas.filter.TiposContatosFilter;
import br.com.codiub.pessoas.repository.TiposContatosRepository;
import br.com.codiub.pessoas.service.TiposContatosService;

@RestController
@RequestMapping("/tiposContatos")
public class TiposContatosResource {

  @Autowired private TiposContatosRepository tiposContatosRepository;

  @Autowired private TiposContatosService tiposContatosService;

  //AA - Inserir
  @PostMapping
  public ResponseEntity<TiposContatos> criar(@RequestBody TiposContatosInput tiposContatosInput, HttpServletResponse response) {
	  TiposContatos tiposContatosSalva = tiposContatosService.save(tiposContatosInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(tiposContatosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<TiposContatos> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TiposContatos> tiposContatos = tiposContatosRepository.findById(codigo);
    return tiposContatos != null
        ? ResponseEntity.ok(tiposContatos.get())
        : ResponseEntity.notFound().build();
  }
  

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    tiposContatosRepository.deleteById(codigo);
  }
  
  //AA - Update
  @PutMapping("/{codigo}")
	public ResponseEntity<TiposContatos> atualizar(@PathVariable Long codigo,@Validated @RequestBody TiposContatosInput tiposContatosInput) {
	  TiposContatos tiposContatosSalva = tiposContatosService.atualizar(codigo, tiposContatosInput);
	return ResponseEntity.ok(tiposContatosSalva);
  }

  @GetMapping
  public Page<TiposContatos> pesquisar(TiposContatosFilter tiposContatosFilter, Pageable pageable) {
    return tiposContatosRepository.filtrar(tiposContatosFilter, pageable);
  }
}
