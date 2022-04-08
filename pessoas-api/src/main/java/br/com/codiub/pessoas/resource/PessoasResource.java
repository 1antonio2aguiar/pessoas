package br.com.codiub.pessoas.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
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

import br.com.codiub.pessoas.Input.DadosPfInput;
import br.com.codiub.pessoas.Input.DistritosInput;
import br.com.codiub.pessoas.Input.PessoasInput;
import br.com.codiub.pessoas.entity.DadosPf;
import br.com.codiub.pessoas.entity.Distritos;
import br.com.codiub.pessoas.entity.Etnias;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.entity.TiposEstadosCivis;
import br.com.codiub.pessoas.filter.PessoasFilter;
import br.com.codiub.pessoas.repository.DadosPfRepository;
import br.com.codiub.pessoas.repository.DistritosRepository;
import br.com.codiub.pessoas.repository.EtniasRepository;
import br.com.codiub.pessoas.repository.PessoasRepository;
import br.com.codiub.pessoas.repository.TiposEstadosCivisRepository;
import br.com.codiub.pessoas.service.PessoasService;

@RestController
@RequestMapping("/pessoas")
public class PessoasResource {

	@Autowired private PessoasRepository pessoasRepository;
	@Autowired private DadosPfRepository dadosPfRepository;
	@Autowired private EtniasRepository etniasRepository;
	@Autowired private TiposEstadosCivisRepository tiposEstadosCivisRepository;
	@Autowired private DistritosRepository distritosRepository;
	@Autowired private PessoasService pessoasService;
  
	//Tonhas 01072021 - insert
	@PostMapping("/completo")
	public ResponseEntity<Object> criar(@RequestBody PessoasInput pessoasInput, HttpServletResponse response) {
		ResponseEntity<Object> resp = pessoasService.save(pessoasInput);
		return resp;
	}
	
	//Tonhas 29072021 - Update
	@PutMapping("/{codigo}")
	public ResponseEntity<Object> atualizar(@PathVariable Long codigo, @Validated @RequestBody PessoasInput pessoasInput) {
		
		ResponseEntity<Object> pessoasSalva = pessoasService.atualizar(codigo, pessoasInput);
	    return ResponseEntity.ok(pessoasSalva);
	}
  
	//Tonhas edit
	@GetMapping("/{codigo}")
	public ResponseEntity<PessoasInput> buscarPeloCodigo(@PathVariable Long codigo, PessoasInput pessoasInput) {
		//System.err.println("OLHA ONDE ELE VÊIO !! " );
	  
		Optional<Pessoas> pessoas = pessoasRepository.findById(codigo);
	  
		if(pessoas.isPresent()) {
			BeanUtils.copyProperties(pessoas.get(), pessoasInput);
		  
			//Busca em dados pf
			Optional<DadosPf> dadosPfList = dadosPfRepository.findById(codigo);
		  
			if(dadosPfList.isPresent()) {
				DadosPfInput dadosPfInput = new DadosPfInput();
				
	   			// Copia dadosPfList para dadosPfInput
				BeanUtils.copyProperties(dadosPfList.get(), dadosPfInput);
				
				if(dadosPfList.get().getEstadoCivil() != null){
					TiposEstadosCivis tiposEstadosCivis = tiposEstadosCivisRepository.findById(dadosPfList.get().getEstadoCivil()).get();
					dadosPfInput.setTiposEstadosCivis(tiposEstadosCivis);
				} else {
					TiposEstadosCivis tiposEstadosCivis = new TiposEstadosCivis();
					dadosPfInput.setTiposEstadosCivis(tiposEstadosCivis);
				}
				
				if(dadosPfList.get().getEtnia() != null){
					Etnias etnias = etniasRepository.findById(dadosPfList.get().getEtnia()).get();
					dadosPfInput.setEtnias(etnias);
				} else {
					Etnias etnias = new Etnias();
					dadosPfInput.setEtnias(etnias);
				}
				
				if(dadosPfList.get().getLocalNascimento() != null) {
					Distritos distritos = distritosRepository.findById(dadosPfList.get().getLocalNascimento()).get();
					
					long distritoId =  distritos.getId();
					String distritoNome = (distritos.getCidades().getNome() + " " + distritos.getNome() + " - " + 
							distritos.getCidades().getEstados().getSigla());
					
					DistritosInput distritosInput = new DistritosInput(distritoId,distritoNome,null);
					
					dadosPfInput.setDistritos(distritosInput);
				}
				
				pessoasInput.setDadosPf(dadosPfInput);
				
				//System.err.println("BATEU AQUI !! " + dadosPfInput.getCpf());
			}
		}
		return pessoasInput != null ? ResponseEntity.ok(pessoasInput) : ResponseEntity.notFound().build();
	}
	
	// Aqui busca os dados para gerar a lista.
	@GetMapping()
	public Page<Pessoas> pesquisar(PessoasFilter pessoasFilter, Pageable pageable) {
		
		return pessoasRepository.filtrar(pessoasFilter, pageable);
		
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		//System.err.println("BATEU AQUI !! "); 
		dadosPfRepository.deleteById(codigo);
		pessoasRepository.deleteById(codigo);
	}

}
