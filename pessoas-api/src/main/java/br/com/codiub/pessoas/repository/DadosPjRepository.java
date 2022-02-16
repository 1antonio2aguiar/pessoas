package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.DadosPj;
import br.com.codiub.pessoas.repository.dadosPj.DadosPjRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosPjRepository extends JpaRepository<DadosPj, Long>, DadosPjRepositoryQuery {}
