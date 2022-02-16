package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.Logradouros;
import br.com.codiub.pessoas.repository.logradouros.LogradourosRepositoryQuery;

public interface LogradourosRepository extends JpaRepository<Logradouros, Long>, LogradourosRepositoryQuery{

}
