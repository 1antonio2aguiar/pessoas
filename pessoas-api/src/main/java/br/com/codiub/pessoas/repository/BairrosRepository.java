package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.Bairros;
import br.com.codiub.pessoas.repository.bairros.BairrosRepositoryQuery;

public interface BairrosRepository extends JpaRepository<Bairros, Long>, BairrosRepositoryQuery{

}
