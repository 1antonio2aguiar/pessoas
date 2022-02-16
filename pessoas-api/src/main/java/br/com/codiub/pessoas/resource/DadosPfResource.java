package br.com.codiub.pessoas.resource;

import br.com.codiub.pessoas.entity.DadosPf;
import br.com.codiub.pessoas.filter.DadosPfFilter;
import br.com.codiub.pessoas.repository.DadosPfRepository;
import br.com.codiub.pessoas.service.DadosPfService;
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
@RequestMapping("/dadosPf")
public class DadosPfResource {

  @Autowired private DadosPfRepository dadosPfRepository;

  @Autowired private DadosPfService dadosPfService;

  @PostMapping
  public ResponseEntity<DadosPf> criar(@RequestBody DadosPf dadosPf, HttpServletResponse response) {
    DadosPf dadosPfSalva = dadosPfRepository.save(dadosPf);
    return ResponseEntity.status(HttpStatus.CREATED).body(dadosPfSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<DadosPf> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<DadosPf> dadosPf = dadosPfRepository.findById(codigo);
    return dadosPf != null ? ResponseEntity.ok(dadosPf.get()) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    dadosPfRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<DadosPf> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody DadosPf dadosPf) {
    DadosPf dadosPfSalva = dadosPfService.atualizar(codigo, dadosPf);
    return ResponseEntity.ok(dadosPfSalva);
  }

  @GetMapping
  public Page<DadosPf> pesquisar(DadosPfFilter dadosPfFilter, Pageable pageable) {
    return dadosPfRepository.filtrar(dadosPfFilter, pageable);
  }
}
