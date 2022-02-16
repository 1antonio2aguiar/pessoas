package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.Pessoas;
import br.com.codiub.pessoas.repository.pessoas.PessoasRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoasRepository extends JpaRepository<Pessoas, Long>, PessoasRepositoryQuery {
	
}
