package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.Profissoes;
import br.com.codiub.pessoas.repository.profissoes.ProfissoesRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissoesRepository
    extends JpaRepository<Profissoes, Long>, ProfissoesRepositoryQuery {}
