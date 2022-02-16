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

import br.com.codiub.pessoas.Input.TiposEstadosCivisInput;
import br.com.codiub.pessoas.entity.TiposEstadosCivis;
import br.com.codiub.pessoas.filter.TiposEstadosCivisFilter;
import br.com.codiub.pessoas.repository.TiposEstadosCivisRepository;
import br.com.codiub.pessoas.service.TiposEstadosCivisService;

@RestController
@RequestMapping("/tiposEstadosCivis")
public class TiposEstadosCivisResource {

  @Autowired private TiposEstadosCivisRepository tiposEstadosCivisRepository;

  @Autowired private TiposEstadosCivisService tiposEstadosCivisService;

  //AA - Inserir
  @PostMapping
  public ResponseEntity<TiposEstadosCivis> criar(
	@RequestBody TiposEstadosCivisInput tiposEstadosCivisInput, HttpServletResponse response) {
	  TiposEstadosCivis tiposEstadosCivisSalva = tiposEstadosCivisService.save(tiposEstadosCivisInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(tiposEstadosCivisSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<TiposEstadosCivis> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TiposEstadosCivis> tiposEstadosCivis = tiposEstadosCivisRepository.findById(codigo);
    return tiposEstadosCivis != null
        ? ResponseEntity.ok(tiposEstadosCivis.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    tiposEstadosCivisRepository.deleteById(codigo);
  }

  //AA - Update
  @PutMapping("/{codigo}")
	public ResponseEntity<TiposEstadosCivis> atualizar(
	@PathVariable Long codigo,@Validated @RequestBody TiposEstadosCivisInput tiposEstadosCivisInput) {
	  TiposEstadosCivis tiposEstadosCivisSalva = tiposEstadosCivisService.atualizar(codigo, tiposEstadosCivisInput);
	return ResponseEntity.ok(tiposEstadosCivisSalva);
  }

  @GetMapping
  public Page<TiposEstadosCivis> pesquisar(
      TiposEstadosCivisFilter tiposEstadosCivisFilter, Pageable pageable) {
    return tiposEstadosCivisRepository.filtrar(tiposEstadosCivisFilter, pageable);
  }
}
