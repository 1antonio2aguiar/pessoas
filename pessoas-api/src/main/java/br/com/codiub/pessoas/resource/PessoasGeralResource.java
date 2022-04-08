package br.com.codiub.pessoas.resource;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codiub.pessoas.Input.DadosPfInput;
import br.com.codiub.pessoas.Input.DistritosInput;
import br.com.codiub.pessoas.Input.PessoasInput;
import br.com.codiub.pessoas.entity.DadosPf;
import br.com.codiub.pessoas.entity.Distritos;
import br.com.codiub.pessoas.entity.Etnias;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.entity.PessoasGeral;
import br.com.codiub.pessoas.entity.TiposEstadosCivis;
import br.com.codiub.pessoas.filter.PessoasGeralFilter;
import br.com.codiub.pessoas.repository.DadosPfRepository;
import br.com.codiub.pessoas.repository.DistritosRepository;
import br.com.codiub.pessoas.repository.EtniasRepository;
import br.com.codiub.pessoas.repository.PessoasGeralRepository;
import br.com.codiub.pessoas.repository.PessoasRepository;
import br.com.codiub.pessoas.repository.TiposEstadosCivisRepository;

@RestController
@RequestMapping("/pessoasGeral")
public class PessoasGeralResource {
	
	@Autowired private PessoasGeralRepository pessoasGeralRepository;
	@Autowired private DadosPfRepository dadosPfRepository;
	@Autowired private EtniasRepository etniasRepository;
	@Autowired private TiposEstadosCivisRepository tiposEstadosCivisRepository;
	@Autowired private DistritosRepository distritosRepository;
	//@Autowired private PessoasService pessoasGeralService;
	
	@GetMapping
	public Page<PessoasGeral> pesquisar(PessoasGeralFilter pessoasGeralFilter, Pageable pageable) {
		
		//System.err.println("Entrou na bagaça!@$? ");
		
		return pessoasGeralRepository.filtrar(pessoasGeralFilter, pageable);
		
		
	}
	
	//Tonhas edit
	@GetMapping("/{codigo}")
	public ResponseEntity<PessoasInput> buscarPeloCodigo(@PathVariable Long codigo, PessoasInput pessoasInput) {
		//System.err.println("OLHA ONDE ELE VÊIO !! ");
		
		Optional<PessoasGeral> pessoasGeral = pessoasGeralRepository.findById(codigo);
		
		if(pessoasGeral.isPresent()) {
			
			if(pessoasGeral.get().getFisicaJuridica().equals("F")) {
				
				BeanUtils.copyProperties(pessoasGeral.get(), pessoasInput);
			  
				//Busca em dados pf
				Optional<DadosPf> dadosPfList = dadosPfRepository.findById(codigo);
			  
				if(dadosPfList.isPresent()) {
					DadosPfInput dadosPfInput = new DadosPfInput();
					
					// Copia dadosPfList para dadosPfInput
					BeanUtils.copyProperties(dadosPfList.get(), dadosPfInput);
					
					pessoasInput.setCpfCnpj(dadosPfInput.getCpf());
					
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
					
					//String cpfCnpj = "64043827687" ;//dadosPfInput.getCpf();
					//System.err.println("OLHA ONDE ELE VÊIO !! CPF " + cpfCnpj);
					//cpfCnpj = cpfCnpj.replace(^(\d{3})(\d{3})(\d{3})(\d{2}), "$1.$2.$3-$4");
					//System.err.println("OLHA ONDE ELE VÊIO !! CPF FORMATADO " + cpfCnpj);
					//pessoasInput.setCpfCnpj(cpfCnpj);
					
					pessoasInput.setDadosPf(dadosPfInput);
					
					//System.err.println("BATEU AQUI !! " + dadosPfInput.getCpf());
				}
			} else {
				System.err.println("ENTROU PELO ELSE AQUI!! ");
			}
		}
		
		return pessoasInput != null ? ResponseEntity.ok(pessoasInput) : ResponseEntity.notFound().build();
		
	}
}
