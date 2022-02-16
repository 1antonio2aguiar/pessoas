package br.com.codiub.pessoas.resource;

import br.com.codiub.pessoas.Input.TiposPessoasInput;
import br.com.codiub.pessoas.entity.TiposPessoas;
import br.com.codiub.pessoas.filter.TiposPessoasFilter;
import br.com.codiub.pessoas.repository.TiposPessoasRepository;
import br.com.codiub.pessoas.service.TiposPessoasService;
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
@RequestMapping("/tiposPessoas")
public class TiposPessoasResource {

  @Autowired private TiposPessoasRepository tiposPessoasRepository;

  @Autowired private TiposPessoasService tiposPessoasService;

  //AA - Inserir
  @PostMapping
  public ResponseEntity<TiposPessoas> criar(@RequestBody TiposPessoasInput tiposPessoasInput, HttpServletResponse response) {
	  TiposPessoas tiposPessoasSalva = tiposPessoasService.save(tiposPessoasInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(tiposPessoasSalva);
  }
  

  @GetMapping("/{codigo}")
  public ResponseEntity<TiposPessoas> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<TiposPessoas> tiposPessoas = tiposPessoasRepository.findById(codigo);
    return tiposPessoas != null
        ? ResponseEntity.ok(tiposPessoas.get())
        : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    tiposPessoasRepository.deleteById(codigo);
  }
  
  //AA - Update
  @PutMapping("/{codigo}")
	public ResponseEntity<TiposPessoas> atualizar(@PathVariable Long codigo,@Validated @RequestBody TiposPessoasInput TiposPessoasInput) {
	TiposPessoas tiposPessoasSalva = tiposPessoasService.atualizar(codigo, TiposPessoasInput);
	return ResponseEntity.ok(tiposPessoasSalva);
  }

  @GetMapping
  public Page<TiposPessoas> pesquisar(TiposPessoasFilter tiposPessoasFilter, Pageable pageable) {
    return tiposPessoasRepository.filtrar(tiposPessoasFilter, pageable);
  }
}
