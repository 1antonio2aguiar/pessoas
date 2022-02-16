package br.com.codiub.pessoas.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.Input.DadosPjInput;
import br.com.codiub.pessoas.Input.PessoasJuridicaInput;
import br.com.codiub.pessoas.entity.DadosPj;
import br.com.codiub.pessoas.entity.PessoasJuridica;
import br.com.codiub.pessoas.entity.TiposEmpresas;
import br.com.codiub.pessoas.filter.PessoasJuridicaFilter;
import br.com.codiub.pessoas.repository.DadosPjRepository;
import br.com.codiub.pessoas.repository.PessoasJuridicaRepository;
import br.com.codiub.pessoas.repository.TiposEmpresasRepository;
import br.com.codiub.pessoas.service.PessoasJuridicaService;


@RestController
@RequestMapping("/pessoasJuridica")
public class PessoasJuridicaResource {
	
	@Autowired private PessoasJuridicaRepository pessoasJuridicaRepository;
	@Autowired private DadosPjRepository dadosPjRepository;
	@Autowired private PessoasJuridicaService pessoasJuridicaService;
	@Autowired private TiposEmpresasRepository tiposEmpresasRepository;
	
	//Tonhas 29112021 - insert
	@PostMapping
	public ResponseEntity<Object> criar(@RequestBody PessoasJuridicaInput pessoasJuridicaInput, HttpServletResponse response) {
		ResponseEntity<Object> resp = pessoasJuridicaService.save(pessoasJuridicaInput);
		return resp;
	}
	
	// Aqui busca os dados para gerar a lista.
	@GetMapping()
	public Page<PessoasJuridica> pesquisar(PessoasJuridicaFilter pessoasJuridicaFilter, Pageable pageable) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
		return pessoasJuridicaRepository.filtrar(pessoasJuridicaFilter, pageable);
	}
	
	//Tonhas 29112021 - Update
	@PutMapping("/{codigo}")
	public ResponseEntity<Object> atualizar(@PathVariable Long codigo, @Validated @RequestBody PessoasJuridicaInput pessoasJuridicaInput) {
		
		ResponseEntity<Object> pessoasJuridicaSalva = pessoasJuridicaService.atualizar(codigo, pessoasJuridicaInput);
	    return ResponseEntity.ok(pessoasJuridicaSalva);
	}
	
	//Tonhas 29112021 - edit
	@GetMapping("/{codigo}")
	public ResponseEntity<PessoasJuridicaInput> buscarPeloCodigo(@PathVariable Long codigo, PessoasJuridicaInput pessoasJuridicaInput) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
	  
		Optional<PessoasJuridica> pessoasJuridica = pessoasJuridicaRepository.findById(codigo);
	  
		if(pessoasJuridica.isPresent()) {
			BeanUtils.copyProperties(pessoasJuridica.get(), pessoasJuridicaInput);
		  
			//Busca em dados pf
			Optional<DadosPj> dadosPjList = dadosPjRepository.findById(codigo);
		  
			if(dadosPjList.isPresent()) {
				DadosPjInput dadosPjInput = new DadosPjInput();
				
	   			// Copia dadosPfList para dadosPfInput
				BeanUtils.copyProperties(dadosPjList.get(), dadosPjInput);
				
				if(dadosPjList.get().getTipoEmpresa() != null){
					TiposEmpresas tiposEmpresas = tiposEmpresasRepository.findById(dadosPjList.get().getTipoEmpresa()).get();
					dadosPjInput.setTiposEmpresas(tiposEmpresas);
				} else {
					TiposEmpresas tiposEmpresas = new TiposEmpresas();
					dadosPjInput.setTiposEmpresas(tiposEmpresas);
				}
				
				pessoasJuridicaInput.setDadosPj(dadosPjInput);
				
				//System.err.println("BATEU AQUI !! " + dadosPfInput.getCpf());
			}
		}
		return pessoasJuridicaInput != null ? ResponseEntity.ok(pessoasJuridicaInput) : ResponseEntity.notFound().build();
	}

}




