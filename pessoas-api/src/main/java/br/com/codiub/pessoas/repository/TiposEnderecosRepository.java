package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.TiposEnderecos;
import br.com.codiub.pessoas.repository.tiposEnderecos.TiposEnderecosRepositoryQuery;

public interface TiposEnderecosRepository extends JpaRepository<TiposEnderecos, Long>, TiposEnderecosRepositoryQuery {

}
