package br.com.codiub.pessoas.resource;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.Input.ContatosInput;
import br.com.codiub.pessoas.Input.DocumentosInput;
import br.com.codiub.pessoas.Input.EmpresasPessoasInput;
import br.com.codiub.pessoas.entity.Contatos;
import br.com.codiub.pessoas.entity.EmpresasPessoas;
import br.com.codiub.pessoas.entity.Enderecos;
import br.com.codiub.pessoas.filter.EmpresasPessoasFilter;
import br.com.codiub.pessoas.repository.EmpresasPessoasRepository;
import br.com.codiub.pessoas.service.EmpresasPessoasService;

@RestController
@RequestMapping("/empresasPessoas")
public class EmpresasPessoasResource {
	
	@Autowired private EmpresasPessoasRepository empresasPessoasRepository;
	@Autowired private EmpresasPessoasService empresasPessoasService;
	
	//Tonhas 15022022 - insert
	@PostMapping("/novo")
	public ResponseEntity<Object> criar(@RequestBody EmpresasPessoasInput empresasPessoasInput, HttpServletResponse response) {
		ResponseEntity<Object> resp = empresasPessoasService.save(empresasPessoasInput);
		return resp;
	}
	
	//AA - 22/02/2022 Update
	@PutMapping("/{codigo}")
	public ResponseEntity<EmpresasPessoas> atualizar(@PathVariable Long codigo, @Validated @RequestBody EmpresasPessoasInput empresasPessoasInput) {
		EmpresasPessoas empresasPessoasSalva = empresasPessoasService.atualizar(codigo, empresasPessoasInput);
	    return ResponseEntity.ok(empresasPessoasSalva);
	}
	
	// Aqui busca os dados para gerar a lista.
	@GetMapping()
	public Page<EmpresasPessoas> pesquisar(EmpresasPessoasFilter empresasPessoasFilter, Pageable pageable) {
		//System.err.println("OLHA ONDE ELE VÊIO !! NENEM " );
		return empresasPessoasRepository.filtrar(empresasPessoasFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<EmpresasPessoas> buscarPeloCodigo(@PathVariable Long codigo) {
		//System.err.println("OLHA ONDE ELE VÊIO !! PAPAI " );
	    Optional<EmpresasPessoas> empresasPessoas = empresasPessoasRepository.findById(codigo);
	    return empresasPessoas != null
	        ? ResponseEntity.ok(empresasPessoas.get())
	        : ResponseEntity.notFound().build();
	}
	
	//Tonhas busca lista de socios/proprietario da empresa
	@GetMapping(value = "findByIdEmpresaId")
	@ResponseBody
	public ResponseEntity<List<EmpresasPessoas>> findByIdEmpresa(@RequestParam(name = "codigo") Long codigo){
		
		List<EmpresasPessoas> empresasPessoas = empresasPessoasRepository.findByPessoasEmpresasId(codigo);
		return new ResponseEntity<List<EmpresasPessoas>>(empresasPessoas, HttpStatus.OK);
	}
	
	//Tonhas busca lista de empresas da pessoa
	@GetMapping(value = "findByIdPessoaId")
	@ResponseBody
	public ResponseEntity<List<EmpresasPessoas>> findByIdPessoa(@RequestParam(name = "codigo") Long codigo){
		System.err.println("Bateu aqui empresas da pessoa " + codigo);
		List<EmpresasPessoas> empresasPessoas = empresasPessoasRepository.findByPessoasPessoasId(codigo);
		return new ResponseEntity<List<EmpresasPessoas>>(empresasPessoas, HttpStatus.OK);
	}
	
	// Tonhas - 22/02/2022 - Delete
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		System.err.println("BATEU AQUI !!  DELETE " + codigo); 
		empresasPessoasRepository.deleteById(codigo);
	}

}
