package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.PessoasInput;
import br.com.codiub.pessoas.repository.pessoasInput.PessoasInputRepositoryQuery;

public interface PessoasInputRepository extends JpaRepository<PessoasInput, Long>, PessoasInputRepositoryQuery{
	
	//List<PessoasInput> findByPessoasId(Long codigo);

}
