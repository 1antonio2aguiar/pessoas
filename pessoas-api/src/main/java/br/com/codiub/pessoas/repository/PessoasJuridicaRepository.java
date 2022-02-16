package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codiub.pessoas.entity.PessoasJuridica;
import br.com.codiub.pessoas.repository.pessoasJuridica.PessoasJuridicaRepositoryQuery;

public interface PessoasJuridicaRepository extends JpaRepository<PessoasJuridica, Long>, PessoasJuridicaRepositoryQuery{

}
