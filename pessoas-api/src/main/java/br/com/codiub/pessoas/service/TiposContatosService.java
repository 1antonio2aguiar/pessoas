package br.com.codiub.pessoas.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.pessoas.Input.TiposContatosInput;
import br.com.codiub.pessoas.entity.TiposContatos;
import br.com.codiub.pessoas.repository.TiposContatosRepository;

@Service
public class TiposContatosService {

  @Autowired private TiposContatosRepository tiposContatosRepository;

  //AA
  public TiposContatos atualizar(Long codigo, TiposContatosInput tiposContatosInput) {
	  TiposContatos tiposContatosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposContatosInput, tiposContatosSalva, "id");
    return tiposContatosRepository.save(tiposContatosSalva);
  }

  public TiposContatos buscarPeloCodigo(Long codigo) {
    Optional<TiposContatos> tiposContatosSalva = tiposContatosRepository.findById(codigo);
    if (tiposContatosSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return tiposContatosSalva.get();
  }
  
  //AA
  public TiposContatos save(TiposContatosInput tiposContatosInput) {

	  TiposContatos tiposContatos = new TiposContatos();
	  BeanUtils.copyProperties(tiposContatosInput, tiposContatos, "id");
		
	  return tiposContatosRepository.save(tiposContatos);
  }
}
