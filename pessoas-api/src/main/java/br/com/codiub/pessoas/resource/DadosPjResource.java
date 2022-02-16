package br.com.codiub.pessoas.resource;

import br.com.codiub.pessoas.entity.DadosPj;
import br.com.codiub.pessoas.filter.DadosPjFilter;
import br.com.codiub.pessoas.repository.DadosPjRepository;
import br.com.codiub.pessoas.service.DadosPjService;
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
@RequestMapping("/dadosPj")
public class DadosPjResource {

  @Autowired private DadosPjRepository dadosPjRepository;

  @Autowired private DadosPjService dadosPjService;

  @PostMapping
  public ResponseEntity<DadosPj> criar(@RequestBody DadosPj dadosPj, HttpServletResponse response) {
    DadosPj dadosPjSalva = dadosPjRepository.save(dadosPj);
    return ResponseEntity.status(HttpStatus.CREATED).body(dadosPjSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<DadosPj> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<DadosPj> dadosPj = dadosPjRepository.findById(codigo);
    return dadosPj != null ? ResponseEntity.ok(dadosPj.get()) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    dadosPjRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<DadosPj> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody DadosPj dadosPj) {
    DadosPj dadosPjSalva = dadosPjService.atualizar(codigo, dadosPj);
    return ResponseEntity.ok(dadosPjSalva);
  }

  @GetMapping
  public Page<DadosPj> pesquisar(DadosPjFilter dadosPjFilter, Pageable pageable) {
    return dadosPjRepository.filtrar(dadosPjFilter, pageable);
  }
}
