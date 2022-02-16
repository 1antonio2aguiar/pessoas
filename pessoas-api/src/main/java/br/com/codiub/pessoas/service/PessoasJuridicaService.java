package br.com.codiub.pessoas.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.codiub.pessoas.Input.DadosPfInput;
import br.com.codiub.pessoas.Input.DadosPjInput;
import br.com.codiub.pessoas.Input.PessoasInput;
import br.com.codiub.pessoas.Input.PessoasJuridicaInput;
import br.com.codiub.pessoas.entity.DadosPf;
import br.com.codiub.pessoas.entity.DadosPj;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.entity.PessoasJuridica;
import br.com.codiub.pessoas.exceptionhandler.PersonalExceptionHandler.Erro;
import br.com.codiub.pessoas.repository.DadosPjRepository;
import br.com.codiub.pessoas.repository.PessoasJuridicaRepository;

@Service
public class PessoasJuridicaService {
	
	@Autowired private PessoasJuridicaRepository pessoasJuridicaRepository;
	@Autowired private DadosPjRepository dadosPjRepository;
	
	//Tonhas 29112021 - Insere em pessoas
	@Transactional
	public ResponseEntity<Object> save(PessoasJuridicaInput pessoasJuridicaInput) {
		// Chama a função que insere em pessoas
		PessoasJuridica pessoasJuridica = savePessoasJuridica(pessoasJuridicaInput);
		
		saveDadosPj(pessoasJuridica, pessoasJuridicaInput.getDadosPj());
		pessoasJuridicaInput.setId(pessoasJuridica.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoasJuridicaInput);
	}
	
	// função que insere em pessoas Juridica
	private PessoasJuridica savePessoasJuridica(PessoasJuridicaInput pessoasJuridicaInput) {
		PessoasJuridica pessoasJuridicaSalva = new PessoasJuridica();
		BeanUtils.copyProperties(pessoasJuridicaInput, pessoasJuridicaSalva, "id");
		
		pessoasJuridicaSalva.setDataAlteracao(new Date());
		pessoasJuridicaSalva.setDataCadastro(new Date());
		return pessoasJuridicaRepository.save(pessoasJuridicaSalva);
	}
	
	//Tonhas 29112021 - Insere os dados na tabela dados_pj
	private DadosPj saveDadosPj(PessoasJuridica pessoasJuridica, DadosPjInput dadosPjInput) {
		//System.err.println("PASSOU AQUI TAMBEM " + pessoas.getId());
		
		// Dados Pessoas Juridica
		DadosPj dadosPjSalva = new DadosPj();
		
		if(dadosPjInput != null) {
			
			BeanUtils.copyProperties(dadosPjInput, dadosPjSalva);
			
			dadosPjSalva.setId(pessoasJuridica.getId());
			dadosPjSalva.setTipoEmpresa(dadosPjInput.getTiposEmpresas().getTipoEstabelecimento());
		} 
		
		return dadosPjRepository.save(dadosPjSalva);
	}
	
	//Tonhas 29/11/2021 - Faz update em pessoas e dados_pj
	@Transactional
	public ResponseEntity<Object> atualizar(Long codigo,PessoasJuridicaInput pessoasJuridicaInput) {
		
		try {
			//Busca em dados pj
			Optional<DadosPj> dadosPjFind = dadosPjRepository.findById(codigo);
			
			if(dadosPjFind.isPresent()) {
				DadosPj dadosPjSave = dadosPjFind.get();
				
				dadosPjSave.setTipoEmpresa(pessoasJuridicaInput.getDadosPj().getTiposEmpresas().getTipoEstabelecimento());
				
				BeanUtils.copyProperties(pessoasJuridicaInput.getDadosPj(), dadosPjSave , "id");
				
				dadosPjRepository.save(dadosPjSave);
			}
			
			PessoasJuridica pessoasJuridicaSalva = buscarPeloCodigo(codigo);
			BeanUtils.copyProperties(pessoasJuridicaInput, pessoasJuridicaSalva, "id");
			pessoasJuridicaSalva.setDataAlteracao(new Date());
			
			PessoasJuridica save = pessoasJuridicaRepository.save(pessoasJuridicaSalva);
			return ResponseEntity.ok(save);

		} catch (Exception e) {			
			String mensagemUsuario = "Não foi possivel Atualizar os dados";
			String mensagemDesenvolvedor = " "+e.getMessage();
			List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
			return ResponseEntity.badRequest().body(erros);
		}
	}
	
	public PessoasJuridica buscarPeloCodigo(Long codigo) {
		Optional<PessoasJuridica> pessoasJuridicaSalva = pessoasJuridicaRepository.findById(codigo);
		if (pessoasJuridicaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoasJuridicaSalva.get();
	}

}
