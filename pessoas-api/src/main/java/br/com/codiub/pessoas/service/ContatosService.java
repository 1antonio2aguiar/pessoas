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

import br.com.codiub.pessoas.Input.ContatosInput;
import br.com.codiub.pessoas.entity.Contatos;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.entity.TiposContatos;
import br.com.codiub.pessoas.repository.ContatosRepository;
import br.com.codiub.pessoas.repository.PessoasRepository;
import br.com.codiub.pessoas.repository.TiposContatosRepository;

@Service
public class ContatosService {
	
	@Autowired private ContatosRepository contatosRepository;
	@Autowired private PessoasRepository pessoasRepository;
    @Autowired private TiposContatosRepository tiposContatosRepository;
    
    public Optional<Contatos> buscarPorPessoa(Long codigo) {
		Optional<Contatos> contatosList = contatosRepository.findById(codigo);
	    if (contatosList == null) {
	    	throw new EmptyResultDataAccessException(1);
	    }
	    return contatosList;
	}
    
    //Tonhas 13092021 - Insere em contatos
  	@Transactional
  	public ResponseEntity<Object> save(ContatosInput contatosInput) {
  		// Chama a função que insere em contatos
  		Contatos contatos = saveContatos(contatosInput);
  		
  		contatosInput.setId(contatos.getId());
  		return ResponseEntity.status(HttpStatus.CREATED).body(contatosInput);
  	}
  	
  	// função que insere em contatos
 	private Contatos saveContatos(ContatosInput contatosInput) {
 		System.err.println("OLHA ONDE ELE VÊIO !! " + contatosInput.getContato());
 		
	 	// Pessoas
		long pessoa = contatosInput.getPessoa();
		Pessoas pessoas = pessoasRepository.findById(pessoa).get();
		BeanUtils.copyProperties(pessoas, contatosInput.getPessoas());
		
		// Tipos Contatos
		long tipoContato = contatosInput.getTiposContatos().getId();
		TiposContatos tiposContatos = tiposContatosRepository.findById(tipoContato).get();
		BeanUtils.copyProperties(tiposContatos, contatosInput.getTiposContatos());
		
		Contatos contatosSalva = new Contatos();
		BeanUtils.copyProperties(contatosInput, contatosSalva);
		BeanUtils.copyProperties(contatosInput.getPessoas(), contatosSalva.getPessoas());
		BeanUtils.copyProperties(contatosInput.getTiposContatos(), contatosSalva.getTiposContatos());
		
		//System.err.println("OLHA ONDE ELE VÊIO ! DE NOVO ! " + contatosSalva.getContato());
		
		contatosSalva.setDataAlteracao(new Date());
		return contatosRepository.save(contatosSalva);
 		
 	}
 	
 	//AA - 13/09/2021 Update
	public Contatos atualizar(Long codigo, ContatosInput contatosInput) {
		Contatos contatosSalva = buscarPeloCodigo(codigo);

		BeanUtils.copyProperties(contatosInput, contatosSalva, "id");
		return contatosRepository.save(contatosSalva);
	}
	
	public Contatos buscarPeloCodigo(Long codigo) {
		Optional<Contatos> contatosSalva = contatosRepository.findById(codigo);
		if (contatosSalva == null) {
			throw new EmptyResultDataAccessException(1);
	    }
	    return contatosSalva.get();
	}

}
