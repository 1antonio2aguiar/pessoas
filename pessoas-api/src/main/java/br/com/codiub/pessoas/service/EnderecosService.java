package br.com.codiub.pessoas.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.codiub.pessoas.Input.EnderecosInput;
import br.com.codiub.pessoas.entity.Bairros;
import br.com.codiub.pessoas.entity.Ceps;
import br.com.codiub.pessoas.entity.Enderecos;
import br.com.codiub.pessoas.entity.Logradouros;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.entity.TiposEnderecos;
import br.com.codiub.pessoas.repository.BairrosRepository;
import br.com.codiub.pessoas.repository.CepsRepository;
import br.com.codiub.pessoas.repository.EnderecosRepository;
import br.com.codiub.pessoas.repository.LogradourosRepository;
import br.com.codiub.pessoas.repository.PessoasRepository;
import br.com.codiub.pessoas.repository.TiposEnderecosRepository;

@Service
public class EnderecosService {
	
    @Autowired private EnderecosRepository enderecosRepository;
    @Autowired private BairrosRepository bairrosRepository;
    @Autowired private LogradourosRepository logradourosRepository;
    @Autowired private CepsRepository cepsRepository;
    @Autowired private PessoasRepository pessoasRepository;
    @Autowired private TiposEnderecosRepository tiposEnderecosRepository;
	
	public Optional<Enderecos> buscarPorPessoa(Long codigo) {
		Optional<Enderecos> enderecosList = enderecosRepository.findById(codigo);
	    if (enderecosList == null) {
	    	throw new EmptyResultDataAccessException(1);
	    }
	    return enderecosList;
	}
	
	//Tonhas 17082021 - Insere em enderecos
	@Transactional
	public ResponseEntity<Object> save(EnderecosInput enderecosInput) {
		// Chama a função que insere em enderecos
		Enderecos enderecos = saveEnderecos(enderecosInput);
		
		enderecosInput.setId(enderecos.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(enderecosInput);
	}
	
	// função que insere em enderecos
	private Enderecos saveEnderecos(EnderecosInput enderecosInput) {
		
		// Bairro
		long bairro = enderecosInput.getBairros().getId();
		Bairros bairros = bairrosRepository.findById(bairro).get();
		BeanUtils.copyProperties(bairros, enderecosInput.getBairros());

		// Logradouro
		long logradouro = enderecosInput.getLogradouro();
		Logradouros logradouros = logradourosRepository.findById(logradouro).get();
		BeanUtils.copyProperties(logradouros, enderecosInput.getLogradouros());
		
		// Cep
		long cep = enderecosInput.getCep();
		Ceps ceps = cepsRepository.findById(cep).get();
		BeanUtils.copyProperties(ceps, enderecosInput.getCeps());
		enderecosInput.getCeps().setCep(ceps.getCep());
		
		// Pessoas
		long pessoa = enderecosInput.getPessoa();
		Pessoas pessoas = pessoasRepository.findById(pessoa).get();
		BeanUtils.copyProperties(pessoas, enderecosInput.getPessoas());
		
		// Tipos Enderecos
		long tipoEndereco = enderecosInput.getTiposEnderecos().getId();
		TiposEnderecos tiposEnderecos = tiposEnderecosRepository.findById(tipoEndereco).get();
		BeanUtils.copyProperties(tiposEnderecos, enderecosInput.getTiposEnderecos());
		
		Enderecos enderecosSalva = new Enderecos();
		BeanUtils.copyProperties(enderecosInput, enderecosSalva);
		BeanUtils.copyProperties(enderecosInput.getCeps(), enderecosSalva.getCeps());
		BeanUtils.copyProperties(enderecosInput.getBairros(), enderecosSalva.getBairros());
		BeanUtils.copyProperties(enderecosInput.getLogradouros(), enderecosSalva.getLogradouros());
		BeanUtils.copyProperties(enderecosInput.getPessoas(), enderecosSalva.getPessoas());
		BeanUtils.copyProperties(enderecosInput.getTiposEnderecos(), enderecosSalva.getTiposEnderecos());
		
		enderecosSalva.setDataAlteracao(new Date());
		return enderecosRepository.save(enderecosSalva);
	}
	
	//AA - 02/09/2021 Update
	public Enderecos atualizar(Long codigo, EnderecosInput enderecosInput) {
		Enderecos enderecosSalva = buscarPeloCodigo(codigo);

		BeanUtils.copyProperties(enderecosInput, enderecosSalva, "id");
		return enderecosRepository.save(enderecosSalva);
	}
	
	public Enderecos buscarPeloCodigo(Long codigo) {
		Optional<Enderecos> enderecosSalva = enderecosRepository.findById(codigo);
		if (enderecosSalva == null) {
			throw new EmptyResultDataAccessException(1);
	    }
	    return enderecosSalva.get();
	}

}

//System.err.println("Cep: " + enderecosSalva.getCeps().getId() + "  " + enderecosSalva.getCeps().getCep());
//System.err.println("Bairro: " + enderecosSalva.getBairros().getId() + "  " + enderecosSalva.getBairros().getNome());
//System.err.println("Logradouro: " + enderecosSalva.getLogradouros().getId() + "  " + enderecosSalva.getLogradouros().getNome());
//System.err.println("Pessoa: " + enderecosSalva.getPessoas().getId() + "  " + enderecosSalva.getPessoas().getNome());
//System.err.println("Endereço: " + enderecosSalva.getTiposEnderecos().getId() + "  " + enderecosSalva.getTiposEnderecos().getDescricao());

