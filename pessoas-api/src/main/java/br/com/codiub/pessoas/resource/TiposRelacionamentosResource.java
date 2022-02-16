package br.com.codiub.pessoas.resource;

import br.com.codiub.pessoas.entity.TiposRelacionamentos;
import br.com.codiub.pessoas.filter.TiposRelacionamentosFilter;
import br.com.codiub.pessoas.repository.TiposRelacionamentosRepository;
import br.com.codiub.pessoas.service.TiposRelacionamentosService;
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
@RequestMapping("/tiposRelacionamentos")
public class TiposRelacionamentosResource {

  @Autowired private TiposRelacionamentosRepository tiposRelacionamentosRepository;

  @Autowired private TiposRelacionamentosService tiposRelacionamentosService;

  @PostMapping
  public ResponseEntity<TiposRelacionamentos> criar(
      @RequestBody TiposRelacionamentos tiposRelacionamentos, HttpServletResponse response) {
    TiposRelacionamentos tiposRelacionamentosSalva =
        tiposRelacionamentosRepository.save(tiposRelacionamentos);
    return ResponseEntity.status(HttpStatus.CREATED).body(tiposRelacionamentosSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<TiposRelacionamentos> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TiposRelacionamentos> tiposRelacionamentos =
        tiposRelacionamentosRepository.findById(codigo);
    return tiposRelacionamentos != null
        ? ResponseEntity.ok(tiposRelacionamentos.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    tiposRelacionamentosRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<TiposRelacionamentos> atualizar(
      @PathVariable Long codigo,
      @Validated @RequestBody TiposRelacionamentos tiposRelacionamentos) {
    TiposRelacionamentos tiposRelacionamentosSalva =
        tiposRelacionamentosService.atualizar(codigo, tiposRelacionamentos);
    return ResponseEntity.ok(tiposRelacionamentosSalva);
  }

  @GetMapping
  public Page<TiposRelacionamentos> pesquisar(
      TiposRelacionamentosFilter tiposRelacionamentosFilter, Pageable pageable) {
    return tiposRelacionamentosRepository.filtrar(tiposRelacionamentosFilter, pageable);
  }
}
