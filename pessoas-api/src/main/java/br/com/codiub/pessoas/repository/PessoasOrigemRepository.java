package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.codiub.pessoas.entity.PessoasOrigem;
import br.com.codiub.pessoas.repository.pessoasOrigem.PessoasOrigemRepositoryQuery;

public interface PessoasOrigemRepository extends  JpaRepository<PessoasOrigem, Long>, 
	JpaSpecificationExecutor<PessoasOrigem>
	,PessoasOrigemRepositoryQuery	{


}
