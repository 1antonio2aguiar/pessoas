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

import br.com.codiub.pessoas.Input.DocumentosInput;
import br.com.codiub.pessoas.entity.Documentos;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.entity.TiposDocumentos;
import br.com.codiub.pessoas.repository.DocumentosRepository;
import br.com.codiub.pessoas.repository.PessoasRepository;
import br.com.codiub.pessoas.repository.TiposDocumentosRepository;

@Service
public class DocumentosService {
	
	@Autowired private DocumentosRepository documentosRepository;
	@Autowired private PessoasRepository pessoasRepository;
	@Autowired private TiposDocumentosRepository tiposDocumentosRepository;
	
	public Optional<Documentos> buscarPorPessoa(Long codigo) {
		Optional<Documentos> documentosList = documentosRepository.findById(codigo);
	    if (documentosList == null) {
	    	throw new EmptyResultDataAccessException(1);
	    }
	    return documentosList;
	}
	
	//Tonhas 13092021 - Insere em Documentos
  	@Transactional
  	public ResponseEntity<Object> save(DocumentosInput documentosInput) {
  		// Chama a função que insere em documentos
  		Documentos documentos = saveDocumentos(documentosInput);
  		
  		documentosInput.setId(documentos.getId());
  		return ResponseEntity.status(HttpStatus.CREATED).body(documentosInput);
  	}
  	
  	// função que insere em documentos
  	private Documentos saveDocumentos(DocumentosInput documentosInput) {
 	 	// Pessoas
 		long pessoa = documentosInput.getPessoa();
 		Pessoas pessoas = pessoasRepository.findById(pessoa).get();
 		BeanUtils.copyProperties(pessoas, documentosInput.getPessoas());
 		
 		// Tipos Documentos
 		long tipoDocumento = documentosInput.getTiposDocumentos().getId();
 		TiposDocumentos tiposDocumentos = tiposDocumentosRepository.findById(tipoDocumento).get();
 		BeanUtils.copyProperties(tiposDocumentos, documentosInput.getTiposDocumentos());
 		
 		Documentos documentosSalva = new Documentos();
 		BeanUtils.copyProperties(documentosInput, documentosSalva);
 		BeanUtils.copyProperties(documentosInput.getPessoas(), documentosSalva.getPessoas());
 		BeanUtils.copyProperties(documentosInput.getTiposDocumentos(), documentosSalva.getTiposDocumentos());
 		
 		documentosSalva.setDataAlteracao(new Date());
 		return documentosRepository.save(documentosSalva);
  		
  	}
  	
  	//AA - 13/09/2021 Update
  	public Documentos atualizar(Long codigo, DocumentosInput documentosInput) {
  		//System.err.println("BATEU BUNITO AQUI !! ");
  		Documentos documentosSalva = buscarPeloCodigo(codigo);

  		BeanUtils.copyProperties(documentosInput, documentosSalva, "id");
  		return documentosRepository.save(documentosSalva);
  	}
  	
  	public Documentos buscarPeloCodigo(Long codigo) {
  		Optional<Documentos> documentosSalva = documentosRepository.findById(codigo);
  		if (documentosSalva == null) {
  			throw new EmptyResultDataAccessException(1);
  	    }
  	    return documentosSalva.get();
  	}

}
