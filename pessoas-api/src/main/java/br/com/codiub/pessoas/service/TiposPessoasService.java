package br.com.codiub.pessoas.service;

import java.util.Optional;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.codiub.pessoas.Input.TiposPessoasInput;
import br.com.codiub.pessoas.entity.TiposPessoas;
import br.com.codiub.pessoas.repository.TiposPessoasRepository;

@Service
public class TiposPessoasService {

  @Autowired private TiposPessoasRepository tiposPessoasRepository;
  
  //AA
  public TiposPessoas atualizar(Long codigo, TiposPessoasInput tiposPessoasInput) {
    TiposPessoas tiposPessoasSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposPessoasInput, tiposPessoasSalva, "id");
    return tiposPessoasRepository.save(tiposPessoasSalva);
  }

  public TiposPessoas buscarPeloCodigo(Long codigo) {
    Optional<TiposPessoas> tiposPessoasSalva = tiposPessoasRepository.findById(codigo);
    if (tiposPessoasSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return tiposPessoasSalva.get();
  }
  
  //AA
  public TiposPessoas save(TiposPessoasInput tiposPessoasInput) {

	  TiposPessoas tiposPessoas = new TiposPessoas();
	  BeanUtils.copyProperties(tiposPessoasInput, tiposPessoas, "id");
		
	  return tiposPessoasRepository.save(tiposPessoas);
  }
}
