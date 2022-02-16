package br.com.codiub.pessoas.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
		
		// codigo de pessoas para idPessoa 
 		long iPpessoa = empresasPessoasInput.getIdPessoa();
		Pessoas pessoas = pessoasRepository.findById(iPpessoa).get();
		BeanUtils.copyProperties(pessoas, empresasPessoasInput.getIdPessoas());
		
		// codigo de pessoas para idEmpresa
		long idEmpresa = empresasPessoasInput.getIdEmpresa();
		Pessoas empresas = pessoasRepository.findById(idEmpresa).get();
		BeanUtils.copyProperties(empresas, empresasPessoasInput.getIdEmpresas());
		
		
		// Tipos Vinculo Empresa
 		long tipoVinculo = empresasPessoasInput.getTiposVinculosEmpresas().getId();
 		TiposVinculosEmpresas tiposVinculosEmpresas = tiposVinculosEmpresasRepository.findById(tipoVinculo).get();
 		BeanUtils.copyProperties(tiposVinculosEmpresas, empresasPessoasInput.getTiposVinculosEmpresas());

		EmpresasPessoas empresasPessoasSalva = new EmpresasPessoas();
		
		BeanUtils.copyProperties(empresasPessoasInput, empresasPessoasSalva);
 		BeanUtils.copyProperties(empresasPessoasInput.getIdPessoas(), empresasPessoasSalva.getPessoasPessoas());
 		BeanUtils.copyProperties(empresasPessoasInput.getTiposVinculosEmpresas(), empresasPessoasSalva.getTiposVinculosEmpresas());
		
		return empresasPessoasRepository.save(empresasPessoasSalva);
	
	}	
	
	
}
