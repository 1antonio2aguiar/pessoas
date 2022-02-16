package br.com.codiub.pessoas.service;

import br.com.codiub.pessoas.Input.SituacoesInput;
import br.com.codiub.pessoas.entity.Situacoes;
import br.com.codiub.pessoas.repository.SituacoesRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class SituacoesService {

  @Autowired private SituacoesRepository situacoesRepository;
  
  // AA
  public Situacoes atualizar(Long codigo, SituacoesInput situacoesInput) {
    Situacoes situacoesSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(situacoesInput, situacoesSalva, "id");
    return situacoesRepository.save(situacoesSalva);
  }

  public Situacoes buscarPeloCodigo(Long codigo) {
    Optional<Situacoes> situacoesSalva = situacoesRepository.findById(codigo);
    if (situacoesSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return situacoesSalva.get();
  }
  
  public Situacoes save(SituacoesInput situacoesInput) {
	  Situacoes situacoes = new Situacoes();
	  
	  BeanUtils.copyProperties(situacoesInput, situacoes, "id");
	  return situacoesRepository.save(situacoes);
	  
  }
}
