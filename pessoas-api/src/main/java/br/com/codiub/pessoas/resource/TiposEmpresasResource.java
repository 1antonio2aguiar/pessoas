package br.com.codiub.pessoas.resource;

import br.com.codiub.pessoas.entity.TiposEmpresas;
import br.com.codiub.pessoas.filter.TiposEmpresasFilter;
import br.com.codiub.pessoas.repository.TiposEmpresasRepository;
import br.com.codiub.pessoas.service.TiposEmpresasService;
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
@RequestMapping("/tiposEmpresas")
public class TiposEmpresasResource {

  @Autowired private TiposEmpresasRepository tiposEmpresasRepository;

  @Autowired private TiposEmpresasService tiposEmpresasService;

  @PostMapping
  public ResponseEntity<TiposEmpresas> criar(
      @RequestBody TiposEmpresas tiposEmpresas, HttpServletResponse response) {
    TiposEmpresas tiposEmpresasSalva = tiposEmpresasRepository.save(tiposEmpresas);
    return ResponseEntity.status(HttpStatus.CREATED).body(tiposEmpresasSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<TiposEmpresas> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TiposEmpresas> tiposEmpresas = tiposEmpresasRepository.findById(codigo);
    return tiposEmpresas != null
        ? ResponseEntity.ok(tiposEmpresas.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    tiposEmpresasRepository.deleteById(codigo);
  }

  @PutMapping("/{codigo}")
  public ResponseEntity<TiposEmpresas> atualizar(
      @PathVariable Long codigo, @Validated @RequestBody TiposEmpresas tiposEmpresas) {
    TiposEmpresas tiposEmpresasSalva = tiposEmpresasService.atualizar(codigo, tiposEmpresas);
    return ResponseEntity.ok(tiposEmpresasSalva);
  }

  @GetMapping
  public Page<TiposEmpresas> pesquisar(TiposEmpresasFilter tiposEmpresasFilter, Pageable pageable) {
    return tiposEmpresasRepository.filtrar(tiposEmpresasFilter, pageable);
  }
}
