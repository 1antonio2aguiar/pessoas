package br.com.codiub.pessoas.resource;

import br.com.codiub.pessoas.Input.EtniasInput;
import br.com.codiub.pessoas.Input.TiposContatosInput;
import br.com.codiub.pessoas.entity.Etnias;
import br.com.codiub.pessoas.entity.TiposContatos;
import br.com.codiub.pessoas.filter.EtniasFilter;
import br.com.codiub.pessoas.repository.EtniasRepository;
import br.com.codiub.pessoas.service.EtniasService;
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
@RequestMapping("/etnias")
public class EtniasResource {

  @Autowired private EtniasRepository etniasRepository;

  @Autowired private EtniasService etniasService;
  
  //AA - Inserir
  @PostMapping
  public ResponseEntity<Etnias> criar(@RequestBody EtniasInput etniasInput, HttpServletResponse response) {
	  Etnias etniasSalva = etniasService.save(etniasInput);
		return ResponseEntity.status(HttpStatus.CREATED).body(etniasSalva);
  }
  
  @GetMapping("/{codigo}")
  public ResponseEntity<Etnias> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Etnias> etnias = etniasRepository.findById(codigo);
    return etnias != null ? ResponseEntity.ok(etnias.get()) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{codigo}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remover(@PathVariable Long codigo) {
    etniasRepository.deleteById(codigo);
  }
  
  //AA
  @PutMapping("/{codigo}")
  public ResponseEntity<Etnias> atualizar(@PathVariable Long codigo, @Validated @RequestBody EtniasInput etniasInput) {
    Etnias etniasSalva = etniasService.atualizar(codigo, etniasInput);
    return ResponseEntity.ok(etniasSalva);
  }

  @GetMapping
  public Page<Etnias> pesquisar(EtniasFilter etniasFilter, Pageable pageable) {
    return etniasRepository.filtrar(etniasFilter, pageable);
  }
}
