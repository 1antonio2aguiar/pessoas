package br.com.codiub.pessoas.service;

import br.com.codiub.pessoas.entity.TiposRelacionamentos;
import br.com.codiub.pessoas.repository.TiposRelacionamentosRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TiposRelacionamentosService {

  @Autowired private TiposRelacionamentosRepository tiposRelacionamentosRepository;

  public TiposRelacionamentos atualizar(Long codigo, TiposRelacionamentos tiposRelacionamentos) {
    TiposRelacionamentos tiposRelacionamentosSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposRelacionamentos, tiposRelacionamentosSalva, "id");
    return tiposRelacionamentosRepository.save(tiposRelacionamentosSalva);
  }

  public TiposRelacionamentos buscarPeloCodigo(Long codigo) {
    Optional<TiposRelacionamentos> tiposRelacionamentosSalva =
        tiposRelacionamentosRepository.findById(codigo);
    if (tiposRelacionamentosSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return tiposRelacionamentosSalva.get();
  }
}
