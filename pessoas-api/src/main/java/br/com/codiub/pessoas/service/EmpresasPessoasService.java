package br.com.codiub.pessoas.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.codiub.pessoas.Input.EmpresasPessoasInput;
import br.com.codiub.pessoas.entity.EmpresasPessoas;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.entity.TiposVinculosEmpresas;
import br.com.codiub.pessoas.repository.EmpresasPessoasRepository;
import br.com.codiub.pessoas.repository.PessoasRepository;
import br.com.codiub.pessoas.repository.TiposVinculosEmpresasRepository;

@Service
public class EmpresasPessoasService {
	
	@Autowired private EmpresasPessoasRepository empresasPessoasRepository;
	@Autowired private PessoasRepository pessoasRepository;
	@Autowired private TiposVinculosEmpresasRepository tiposVinculosEmpresasRepository;
	
	
	//Tonhas 15022022 - Insere em Pessoas Empresas
	@Transactional
	public ResponseEntity<Object> save(EmpresasPessoasInput empresasPessoasInput) {
  		// Chama a função que insere em empresas pessoas
		EmpresasPessoas empresasPessoas = saveEmpresasPessoas(empresasPessoasInput);
  		
		empresasPessoasInput.setId(empresasPessoasInput.getId());
  		return ResponseEntity.status(HttpStatus.CREATED).body(empresasPessoasInput);
  	}
	
	// função que insere em Empresas Pessoas
	private EmpresasPessoas saveEmpresasPessoas(EmpresasPessoasInput empresasPessoasInput) {
		
		// codigo de pessoas para idEmpresa
		long idEmpresa = empresasPessoasInput.getIdEmpresa();
		Pessoas pessoasEmpresa = pessoasRepository.findById(idEmpresa).get();
		BeanUtils.copyProperties(pessoasEmpresa, empresasPessoasInput.getIdEmpresas());
		
		// codigo de pessoas para idPessoa
		long idPessoa = empresasPessoasInput.getIdPessoa();
		Pessoas pessoasPessoa = pessoasRepository.findById(idPessoa).get();
		BeanUtils.copyProperties(pessoasPessoa, empresasPessoasInput.getIdPessoas());
	
		// Tipos Vinculo Empresa
 		long tipoVinculo = empresasPessoasInput.getTiposVinculosEmpresas().getId();
 		TiposVinculosEmpresas tiposVinculosEmpresas = tiposVinculosEmpresasRepository.findById(tipoVinculo).get();
 		BeanUtils.copyProperties(tiposVinculosEmpresas, empresasPessoasInput.getTiposVinculosEmpresas());
 		
		EmpresasPessoas empresasPessoasSalva = new EmpresasPessoas();
		BeanUtils.copyProperties(empresasPessoasInput, empresasPessoasSalva, "id");
		
 		BeanUtils.copyProperties(empresasPessoasInput.getIdPessoas(), empresasPessoasSalva.getPessoasPessoas());
 		BeanUtils.copyProperties(empresasPessoasInput.getIdEmpresas(), empresasPessoasSalva.getPessoasEmpresas());
 		BeanUtils.copyProperties(empresasPessoasInput.getTiposVinculosEmpresas(), empresasPessoasSalva.getTiposVinculosEmpresas());
		
 		//System.err.println("chegou no grava  " + 4);
 		
		return empresasPessoasRepository.save(empresasPessoasSalva);
	
	}	
	
	//AA - 22/02/2022 Update
	public EmpresasPessoas atualizar(Long codigo, EmpresasPessoasInput empresasPessoasInput) {
		EmpresasPessoas empresasPessoasSalva = buscarPeloCodigo(codigo);

		BeanUtils.copyProperties(empresasPessoasInput, empresasPessoasSalva, "id");
		return empresasPessoasRepository.save(empresasPessoasSalva);
	}
	
	public EmpresasPessoas buscarPeloCodigo(Long codigo) {
		Optional<EmpresasPessoas> empresasPessoasSalva = empresasPessoasRepository.findById(codigo);
		if (empresasPessoasSalva == null) {
			throw new EmptyResultDataAccessException(1);
	    }
	    return empresasPessoasSalva.get();
	}
	
	
}
