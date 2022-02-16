package br.com.codiub.pessoas.service;

import br.com.codiub.pessoas.entity.TiposEmpresas;
import br.com.codiub.pessoas.repository.TiposEmpresasRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TiposEmpresasService {

  @Autowired private TiposEmpresasRepository tiposEmpresasRepository;

  public TiposEmpresas atualizar(Long codigo, TiposEmpresas tiposEmpresas) {
    TiposEmpresas tiposEmpresasSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposEmpresas, tiposEmpresasSalva, "id");
    return tiposEmpresasRepository.save(tiposEmpresasSalva);
  }

  public TiposEmpresas buscarPeloCodigo(Long codigo) {
    Optional<TiposEmpresas> tiposEmpresasSalva = tiposEmpresasRepository.findById(codigo);
    if (tiposEmpresasSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return tiposEmpresasSalva.get();
  }
}
