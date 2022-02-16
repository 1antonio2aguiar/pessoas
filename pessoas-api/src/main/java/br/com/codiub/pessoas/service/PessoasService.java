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
import br.com.codiub.pessoas.Input.PessoasInput;
import br.com.codiub.pessoas.entity.DadosPf;
import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.exceptionhandler.PersonalExceptionHandler.Erro;
import br.com.codiub.pessoas.repository.DadosPfRepository;
import br.com.codiub.pessoas.repository.PessoasRepository;

@Service
public class PessoasService {

	@Autowired private PessoasRepository pessoasRepository;
	@Autowired private DadosPfRepository dadosPfRepository;
  
  	//Tonhas 01072021 - Insere em pessoas
	@Transactional
	public ResponseEntity<Object> save(PessoasInput pessoasInput) {
		// Chama a função que insere em pessoas
		Pessoas pessoas = savePessoas(pessoasInput);
		
		saveDadosPf(pessoas, pessoasInput.getDadosPf());
		pessoasInput.setId(pessoas.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoasInput);
	}
	
	// função que insere em pessoas
	private Pessoas savePessoas(PessoasInput pessoasInput) {
		Pessoas pessoasSalva = new Pessoas();
		BeanUtils.copyProperties(pessoasInput, pessoasSalva, "id");
		
		pessoasSalva.setDataAlteracao(new Date());
		pessoasSalva.setDataCadastro(new Date());
		return pessoasRepository.save(pessoasSalva);
	}
	
	//Tonhas 01072021 - Insere os dados na tabela dados_pf
	private DadosPf saveDadosPf(Pessoas pessoas, DadosPfInput dadosPfInput) {
		//System.err.println("PASSOU AQUI TAMBEM " + pessoas.getId());
		
		// Dados Pessoas Fisica
		DadosPf dadosPfSalva = new DadosPf();
		
		if(dadosPfInput != null) {
			
			BeanUtils.copyProperties(dadosPfInput, dadosPfSalva);
			
			dadosPfSalva.setId(pessoas.getId());
			dadosPfSalva.setEstadoCivil(dadosPfInput.getTiposEstadosCivis().getId());
			dadosPfSalva.setEtnia(dadosPfInput.getEtnias().getId());
		} 
		
		return dadosPfRepository.save(dadosPfSalva);
	}
	
	//Tonhas 30/07/2021 - Faz update em pessoas e dados_pf
	@Transactional
	public ResponseEntity<Object> atualizar(Long codigo,PessoasInput pessoasInput) {
		
		try {
			//Busca em dados pf
			Optional<DadosPf> dadosPfFind = dadosPfRepository.findById(codigo);
			
			if(dadosPfFind.isPresent()) {
				DadosPf dadosPfSave = dadosPfFind.get();
				
				dadosPfSave.setEstadoCivil(pessoasInput.getDadosPf().getTiposEstadosCivis().getId());
				dadosPfSave.setEtnia(pessoasInput.getDadosPf().getEtnias().getId());
				
				BeanUtils.copyProperties(pessoasInput.getDadosPf(), dadosPfSave , "id");
				
				dadosPfRepository.save(dadosPfSave);
			}
			
			Pessoas pessoasSalva = buscarPeloCodigo(codigo);
			BeanUtils.copyProperties(pessoasInput, pessoasSalva, "id");
			pessoasSalva.setDataAlteracao(new Date());
			
			Pessoas save = pessoasRepository.save(pessoasSalva);
			return ResponseEntity.ok(save);

		} catch (Exception e) {			
			String mensagemUsuario = "Não foi possivel Atualizar os dados";
			String mensagemDesenvolvedor = " "+e.getMessage();
			List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
			return ResponseEntity.badRequest().body(erros);
		}
	}

	public Pessoas buscarPeloCodigo(Long codigo) {
		Optional<Pessoas> pessoasSalva = pessoasRepository.findById(codigo);
		if (pessoasSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoasSalva.get();
	}

}



//TiposPessoas tiposPessoas = tiposPessoasRepository.findById(pessoasInput.getTiposPessoas().getId()).get();
//pessoasSalva.setTiposPessoas(tiposPessoas);
//Situacoes situacoes = situacoesRepository.findById(pessoasInput.getSituacoes().getId()).get();
//pessoasSalva.setSituacoes(situacoes);