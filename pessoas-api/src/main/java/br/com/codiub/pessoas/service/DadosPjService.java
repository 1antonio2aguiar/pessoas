package br.com.codiub.pessoas.service;

import br.com.codiub.pessoas.entity.DadosPj;
import br.com.codiub.pessoas.repository.DadosPjRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class DadosPjService {

  @Autowired private DadosPjRepository dadosPjRepository;

  public DadosPj atualizar(Long codigo, DadosPj dadosPj) {
    DadosPj dadosPjSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(dadosPj, dadosPjSalva, "id");
    return dadosPjRepository.save(dadosPjSalva);
  }

  public DadosPj buscarPeloCodigo(Long codigo) {
    Optional<DadosPj> dadosPjSalva = dadosPjRepository.findById(codigo);
    if (dadosPjSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return dadosPjSalva.get();
  }
}
