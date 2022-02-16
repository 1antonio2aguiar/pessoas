package br.com.codiub.pessoas.repository.pessoasJuridica;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.codiub.pessoas.entity.PessoasJuridica;
import br.com.codiub.pessoas.filter.PessoasJuridicaFilter;

public interface PessoasJuridicaRepositoryQuery {
	
	public Page<PessoasJuridica> filtrar(PessoasJuridicaFilter pessoasJuridicaFilter, Pageable pageable);

}

