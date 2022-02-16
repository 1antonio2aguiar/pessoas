package br.com.codiub.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.codiub.pessoas.entity.PessoasGeral;
import br.com.codiub.pessoas.repository.PessoasGeral.PessoasGeralRepositoryQuery;

public interface PessoasGeralRepository extends JpaRepository<PessoasGeral, Long>, PessoasGeralRepositoryQuery{

}

