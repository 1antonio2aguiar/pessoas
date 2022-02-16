package br.com.codiub.pessoas.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.pessoas.Input.EtniasInput;
import br.com.codiub.pessoas.entity.Etnias;
import br.com.codiub.pessoas.repository.EtniasRepository;

@Service
public class EtniasService {

  @Autowired private EtniasRepository etniasRepository;
  
  //AA
  public Etnias atualizar(Long codigo, EtniasInput etniasInput) {
	  Etnias etniasSalva = buscarPeloCodigo(codigo);

	  BeanUtils.copyProperties(etniasInput, etniasSalva, "id");
	  return etniasRepository.save(etniasSalva);
  }

  public Etnias buscarPeloCodigo(Long codigo) {
    Optional<Etnias> etniasSalva = etniasRepository.findById(codigo);
    if (etniasSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return etniasSalva.get();
  }
  
  //AA
  public Etnias save(EtniasInput etniasInput) {

	  Etnias etnias = new Etnias();
	  BeanUtils.copyProperties(etniasInput, etnias, "id");
		
	  return etniasRepository.save(etnias);
  }
  
}
