package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.DadosPf;
import br.com.codiub.pessoas.repository.dadosPf.DadosPfRepositoryQuery;

public interface DadosPfRepository extends JpaRepository<DadosPf, Long>, DadosPfRepositoryQuery {
	
}
