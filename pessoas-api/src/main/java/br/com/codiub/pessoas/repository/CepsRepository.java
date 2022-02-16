package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.Ceps;
import br.com.codiub.pessoas.repository.ceps.CepsRepositoryQuery;

public interface CepsRepository extends JpaRepository<Ceps, Long>, CepsRepositoryQuery {}
