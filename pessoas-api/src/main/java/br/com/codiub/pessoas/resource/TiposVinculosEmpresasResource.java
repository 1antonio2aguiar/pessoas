package br.com.codiub.pessoas.resource;

import br.com.codiub.pessoas.Input.TiposContatosInput;
import br.com.codiub.pessoas.Input.TiposVinculosEmpresasInput;
import br.com.codiub.pessoas.entity.TiposContatos;
import br.com.codiub.pessoas.entity.TiposVinculosEmpresas;
import br.com.codiub.pessoas.filter.TiposVinculosEmpresasFilter;
import br.com.codiub.pessoas.repository.TiposVinculosEmpresasRepository;
import br.com.codiub.pessoas.service.TiposVinculosEmpresasService;
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
@RequestMapping("/tiposVinculosEmpresas")
public class TiposVinculosEmpresasResource {

  @Autowired private TiposVinculosEmpresasRepository tiposVinculosEmpresasRepository;

  @Autowired private TiposVinculosEmpresasService tiposVinculosEmpresasService;

  //AA - Inserir
  @PostMapping
  public ResponseEntity<TiposVinculosEmpresas> criar(
	@RequestBody TiposVinculosEmpresasInput tiposVinculosEmpresasInput, HttpServletResponse response) {
	  TiposVinculosEmpresas tiposVinculosEmpresasSalva = tiposVinculosEmpresasService.save(tiposVinculosEmpresasInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(tiposVinculosEmpresasSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<TiposVinculosEmpresas> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TiposVinculosEmpresas> tiposVinculosEmpresas =
        tiposVinculosEmpresasRepository.findById(codigo);
    return tiposVinculosEmpresas != null
        ? ResponseEntity.ok(tiposVinculosEmpresas.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    tiposVinculosEmpresasRepository.deleteById(codigo);
  }

  //AA - Update
  @PutMapping("/{codigo}")
	public ResponseEntity<TiposVinculosEmpresas> atualizar(
	@PathVariable Long codigo,@Validated @RequestBody TiposVinculosEmpresasInput tiposVinculosEmpresasInput) {
	  TiposVinculosEmpresas tiposVinculosEmpresasSalva = tiposVinculosEmpresasService.atualizar(codigo, tiposVinculosEmpresasInput);
	return ResponseEntity.ok(tiposVinculosEmpresasSalva);
  }

  @GetMapping
  public Page<TiposVinculosEmpresas> pesquisar(
      TiposVinculosEmpresasFilter tiposVinculosEmpresasFilter, Pageable pageable) {
    return tiposVinculosEmpresasRepository.filtrar(tiposVinculosEmpresasFilter, pageable);
  }
}
