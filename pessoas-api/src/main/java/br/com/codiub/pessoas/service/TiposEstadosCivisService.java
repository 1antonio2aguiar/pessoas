package br.com.codiub.pessoas.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.pessoas.Input.TiposEstadosCivisInput;
import br.com.codiub.pessoas.entity.TiposEstadosCivis;
import br.com.codiub.pessoas.repository.TiposEstadosCivisRepository;

@Service
public class TiposEstadosCivisService {

  @Autowired private TiposEstadosCivisRepository tiposEstadosCivisRepository;

  //AA
  public TiposEstadosCivis atualizar(Long codigo, TiposEstadosCivisInput tiposEstadosCivisInput) {
	  TiposEstadosCivis tiposEstadosCivisSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposEstadosCivisInput, tiposEstadosCivisSalva, "id");
    return tiposEstadosCivisRepository.save(tiposEstadosCivisSalva);
  }

  public TiposEstadosCivis buscarPeloCodigo(Long codigo) {
    Optional<TiposEstadosCivis> tiposEstadosCivisSalva =
        tiposEstadosCivisRepository.findById(codigo);
    if (tiposEstadosCivisSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return tiposEstadosCivisSalva.get();
  }
  
  //AA
  public TiposEstadosCivis save(TiposEstadosCivisInput tiposEstadosCivisInput) {

	  TiposEstadosCivis tiposEstadosCivis = new TiposEstadosCivis();
	  BeanUtils.copyProperties(tiposEstadosCivisInput, tiposEstadosCivis, "id");
		
	  return tiposEstadosCivisRepository.save(tiposEstadosCivis);
  }
  
}
