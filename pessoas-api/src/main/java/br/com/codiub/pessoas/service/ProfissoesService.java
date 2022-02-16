package br.com.codiub.pessoas.service;

import br.com.codiub.pessoas.Input.ProfissoesInput;
import br.com.codiub.pessoas.entity.Profissoes;
import br.com.codiub.pessoas.repository.ProfissoesRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProfissoesService {

  @Autowired private ProfissoesRepository profissoesRepository;

  // AA
  public Profissoes atualizar(Long codigo, ProfissoesInput profissoesInput) {
    Profissoes profissoesSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(profissoesInput, profissoesSalva, "id");
    return profissoesRepository.save(profissoesSalva);
  }

  public Profissoes buscarPeloCodigo(Long codigo) {
    Optional<Profissoes> profissoesSalva = profissoesRepository.findById(codigo);
    if (profissoesSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return profissoesSalva.get();
  }
  
  //AA
  public Profissoes save(ProfissoesInput profissoesInput) {

	  Profissoes profissoes = new Profissoes();
	  BeanUtils.copyProperties(profissoesInput, profissoes, "id");
		
	  return profissoesRepository.save(profissoes);
  }
}
