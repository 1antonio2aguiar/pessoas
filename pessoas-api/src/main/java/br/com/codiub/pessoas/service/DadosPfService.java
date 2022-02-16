package br.com.codiub.pessoas.service;

import br.com.codiub.pessoas.entity.DadosPf;
import br.com.codiub.pessoas.repository.DadosPfRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class DadosPfService {

  @Autowired private DadosPfRepository dadosPfRepository;

  public DadosPf atualizar(Long codigo, DadosPf dadosPf) {
    DadosPf dadosPfSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(dadosPf, dadosPfSalva, "id");
    return dadosPfRepository.save(dadosPfSalva);
  }

  public DadosPf buscarPeloCodigo(Long codigo) {
    Optional<DadosPf> dadosPfSalva = dadosPfRepository.findById(codigo);
    if (dadosPfSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return dadosPfSalva.get();
  }
}
