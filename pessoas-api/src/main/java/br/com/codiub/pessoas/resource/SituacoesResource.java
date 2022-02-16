package br.com.codiub.pessoas.resource;

import br.com.codiub.pessoas.Input.SituacoesInput;
import br.com.codiub.pessoas.entity.Situacoes;
import br.com.codiub.pessoas.filter.SituacoesFilter;
import br.com.codiub.pessoas.repository.SituacoesRepository;
import br.com.codiub.pessoas.service.SituacoesService;
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

@RestController
@RequestMapping("/situacoes")
public class SituacoesResource {

  @Autowired private SituacoesRepository situacoesRepository;

  @Autowired private SituacoesService situacoesService;
  
  // AA
  @PostMapping
  public ResponseEntity<Situacoes> criar(@RequestBody SituacoesInput situacoesInput, HttpServletResponse response) {
    Situacoes situacoesSalva = situacoesService.save(situacoesInput);
    return ResponseEntity.status(HttpStatus.CREATED).body(situacoesSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Situacoes> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Situacoes> situacoes = situacoesRepository.findById(codigo);
    return situacoes != null
        ? ResponseEntity.ok(situacoes.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    situacoesRepository.deleteById(codigo);
  }
  
  //AA
  @PutMapping("/{codigo}")
  public ResponseEntity<Situacoes> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody SituacoesInput situacoesInput) {
    Situacoes situacoesSalva = situacoesService.atualizar(codigo, situacoesInput);
    return ResponseEntity.ok(situacoesSalva);
  }

  @GetMapping
  public Page<Situacoes> pesquisar(SituacoesFilter situacoesFilter, Pageable pageable) {
    return situacoesRepository.filtrar(situacoesFilter, pageable);
  }
}
