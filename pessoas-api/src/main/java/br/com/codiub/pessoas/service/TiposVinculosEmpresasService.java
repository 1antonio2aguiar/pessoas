package br.com.codiub.pessoas.service;

import br.com.codiub.pessoas.Input.TiposContatosInput;
import br.com.codiub.pessoas.Input.TiposVinculosEmpresasInput;
import br.com.codiub.pessoas.entity.TiposContatos;
import br.com.codiub.pessoas.entity.TiposVinculosEmpresas;
import br.com.codiub.pessoas.repository.TiposVinculosEmpresasRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TiposVinculosEmpresasService {

  @Autowired private TiposVinculosEmpresasRepository tiposVinculosEmpresasRepository;

  //AA
  public TiposVinculosEmpresas atualizar(Long codigo, TiposVinculosEmpresasInput tiposVinculosEmpresasInput) {
	  TiposVinculosEmpresas tiposVinculosEmpresasSalva = buscarPeloCodigo(codigo);

    BeanUtils.copyProperties(tiposVinculosEmpresasInput, tiposVinculosEmpresasSalva, "id");
    return tiposVinculosEmpresasRepository.save(tiposVinculosEmpresasSalva);
  }

  public TiposVinculosEmpresas buscarPeloCodigo(Long codigo) {
    Optional<TiposVinculosEmpresas> tiposVinculosEmpresasSalva =
        tiposVinculosEmpresasRepository.findById(codigo);
    if (tiposVinculosEmpresasSalva == null) {
      throw new EmptyResultDataAccessException(1);
    }
    return tiposVinculosEmpresasSalva.get();
  }
  
  //AA
  public TiposVinculosEmpresas save(TiposVinculosEmpresasInput tiposVinculosEmpresasInput) {

	  TiposVinculosEmpresas tiposVinculosEmpresas = new TiposVinculosEmpresas();
	  BeanUtils.copyProperties(tiposVinculosEmpresasInput, tiposVinculosEmpresas, "id");
		
	  return tiposVinculosEmpresasRepository.save(tiposVinculosEmpresas);
  }
}
