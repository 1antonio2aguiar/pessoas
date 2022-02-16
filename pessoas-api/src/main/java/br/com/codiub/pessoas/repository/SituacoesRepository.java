package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.Situacoes;
import br.com.codiub.pessoas.repository.situacoes.SituacoesRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SituacoesRepository
    extends JpaRepository<Situacoes, Long>, SituacoesRepositoryQuery {}
