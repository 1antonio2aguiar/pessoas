package br.com.codiub.pessoas.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.pessoas.Input.TiposDocumentosInput;
import br.com.codiub.pessoas.entity.TiposDocumentos;
import br.com.codiub.pessoas.repository.TiposDocumentosRepository;

@Service
public class TiposDocumentosService {

  @Autowired private TiposDocumentosRepository tiposDocumentosRepository;

  public TiposDocumentos atualizar(Long codigo, TiposDocumentos tiposDocumentos) {
    TiposDocumentos tiposDocumentosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposDocumentos, tiposDocumentosSalva, "id");
    return tiposDocumentosRepository.save(tiposDocumentosSalva);
  }
  
  //AA
  public TiposDocumentos atualizar(Long codigo, TiposDocumentosInput tiposDocumentosInput) {
	  TiposDocumentos tiposDocumentosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposDocumentosInput, tiposDocumentosSalva, "id");
    return tiposDocumentosRepository.save(tiposDocumentosSalva);
  }

  public TiposDocumentos buscarPeloCodigo(Long codigo) {
    Optional<TiposDocumentos> tiposDocumentosSalva = tiposDocumentosRepository.findById(codigo);
    if (tiposDocumentosSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return tiposDocumentosSalva.get();
  }
  
  //AA
  public TiposDocumentos save(TiposDocumentosInput tiposDocumentosInput) {

	  TiposDocumentos tiposContatos = new TiposDocumentos();
	  BeanUtils.copyProperties(tiposDocumentosInput, tiposContatos, "id");
		
	  return tiposDocumentosRepository.save(tiposContatos);
  }
}
