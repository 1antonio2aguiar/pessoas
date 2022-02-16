package br.com.codiub.pessoas.repository;

import br.com.codiub.pessoas.entity.TiposPessoas;
import br.com.codiub.pessoas.repository.tiposPessoas.TiposPessoasRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiposPessoasRepository
    extends JpaRepository<TiposPessoas, Long>, TiposPessoasRepositoryQuery {}
