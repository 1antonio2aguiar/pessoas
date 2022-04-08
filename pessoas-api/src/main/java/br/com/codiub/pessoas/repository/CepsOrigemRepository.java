package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.CepsOrigem;
import br.com.codiub.pessoas.repository.cepsOrigem.CepsOrigemRepositoryQuery;

public interface CepsOrigemRepository extends JpaRepository<CepsOrigem, Long >, CepsOrigemRepositoryQuery{

}

