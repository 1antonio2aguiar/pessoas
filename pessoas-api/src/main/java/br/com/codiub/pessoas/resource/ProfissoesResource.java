package br.com.codiub.pessoas.resource;

import br.com.codiub.pessoas.Input.ProfissoesInput;
import br.com.codiub.pessoas.entity.Profissoes;
import br.com.codiub.pessoas.filter.ProfissoesFilter;
import br.com.codiub.pessoas.repository.ProfissoesRepository;
import br.com.codiub.pessoas.service.ProfissoesService;
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
@RequestMapping("/profissoes")
public class ProfissoesResource {

  @Autowired private ProfissoesRepository profissoesRepository;

  @Autowired private ProfissoesService profissoesService;
  
  // AA
  @PostMapping
  public ResponseEntity<Profissoes> criar(
      @RequestBody ProfissoesInput profissoesInput, HttpServletResponse response) {
    Profissoes profissoesSalva = profissoesService.save(profissoesInput);
    return ResponseEntity.status(HttpStatus.CREATED).body(profissoesSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Profissoes> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Profissoes> profissoes = profissoesRepository.findById(codigo);
    return profissoes != null
        ? ResponseEntity.ok(profissoes.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    profissoesRepository.deleteById(codigo);
  }
  
  //AA
  @PutMapping("/{codigo}")
  public ResponseEntity<Profissoes> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody ProfissoesInput profissoesInput) {
    Profissoes profissoesSalva = profissoesService.atualizar(codigo, profissoesInput);
    return ResponseEntity.ok(profissoesSalva);
  }

  @GetMapping
  public Page<Profissoes> pesquisar(ProfissoesFilter profissoesFilter, Pageable pageable) {
    return profissoesRepository.filtrar(profissoesFilter, pageable);
  }
}
