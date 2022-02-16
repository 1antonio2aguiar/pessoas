package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.TiposLogradouros;
import br.com.codiub.pessoas.repository.tiposLogradouros.TiposLogradourosRepositoryQuery;

public interface TiposLogradourosRepository extends JpaRepository<TiposLogradouros, Long>, TiposLogradourosRepositoryQuery{

}
